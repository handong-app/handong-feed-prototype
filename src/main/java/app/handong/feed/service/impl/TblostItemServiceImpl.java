package app.handong.feed.service.impl;

import app.handong.feed.constants.Permission;
import app.handong.feed.domain.TblostItem;
import app.handong.feed.domain.TblostItemFile;
import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemDto;
import app.handong.feed.exception.NoAuthorizationException;
import app.handong.feed.exception.NoMatchingDataException;
import app.handong.feed.mapper.TblostItemMapper;
import app.handong.feed.repository.TblostItemFileRepository;
import app.handong.feed.repository.TblostItemRepository;
import app.handong.feed.service.FirebaseService;
import app.handong.feed.service.TbadminService;
import app.handong.feed.service.TblostItemService;
import app.handong.feed.util.Hasher;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TblostItemServiceImpl implements TblostItemService {

    private final TblostItemRepository tblostItemRepository;
    private final TblostItemFileRepository tblostItemFileRepository;
    private final TblostItemMapper tblostItemMapper;
    private final FirebaseService firebaseService;
    private final TbadminService tbadminService;

    public TblostItemServiceImpl(TblostItemRepository tblostRepository, TblostItemFileRepository tblostItemFileRepository, TblostItemMapper tblostItemMapper, FirebaseService firebaseService, TbadminService tbadminService) {
        this.tblostItemRepository = tblostRepository;
        this.tblostItemFileRepository = tblostItemFileRepository;
        this.tblostItemMapper = tblostItemMapper;
        this.firebaseService = firebaseService;
        this.tbadminService = tbadminService;
    }

    @Override
    @Transactional
    public TblostItemDto.CreateResDto createLostItem(TblostItemDto.CreateReqDto createReqDto, List<MultipartFile> files, String reqUserId) {
        TblostItem tblostItem = TblostItem.of(reqUserId, createReqDto.getLostPersonName(), createReqDto.getContent());
        TblostItem savedLostItem = tblostItemRepository.save(tblostItem);

        List<String> fileUrls = firebaseService.uploadFiles(files, "LostItem", savedLostItem.getId());

        return TblostItemDto.CreateResDto.builder()
                .id(savedLostItem.getId())
                .fileUrls(fileUrls)
                .build();
    }

    @Override
    public TblostItemDto.DetailResDto getLostItemDetail(DefaultDto.IdReqDto idReqDto) {
        TblostItemDto.DetailServDto detail = tblostItemMapper.getLostItemDetailById(idReqDto.getId());
        if (detail == null) {
            throw new NoMatchingDataException("대상 분실물이 존제하지 않습니다.");
        }

        return TblostItemDto.DetailResDto.builder()
                .id(detail.getId())
                .lostPersonName(detail.getLostPersonName())
                .content(detail.getContent())
                .createdAt(detail.getCreatedAt())
                .fileUrls(getFileUrls(detail))
                .build();
    }

    @Override
    public List<TblostItemDto.DetailResDto> getAllLostItems() {
        List<TblostItemDto.DetailServDto> lostItems = tblostItemMapper.getAllLostItems();

        return lostItems.stream()
                .map(this::buildDetailResDto)
                .toList();
    }

    private TblostItemDto.DetailResDto buildDetailResDto(TblostItemDto.DetailServDto item) {
        return TblostItemDto.DetailResDto.builder()
                .id(item.getId())
                .lostPersonName(item.getLostPersonName())
                .content(item.getContent())
                .createdAt(item.getCreatedAt())
                .fileUrls(getFileUrls(item))
                .build();
    }

    private List<String> getFileUrls(TblostItemDto.DetailServDto detail) {
        if (detail.getFileNames() == null || detail.getFileNames().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(detail.getFileNames().split(","))
                .map(fileName -> {
                    log.info("{}", fileName);
                    return firebaseService.generateFileUrl(fileName);
                })
                .toList();
    }

    @Override
    @Transactional
    public TblostItemDto.CreateResDto updateLostItem(TblostItemDto.UpdateServDto updateServDto, List<MultipartFile> files, String reqUserId) {
        String lostItemId = updateServDto.getId();

        // 0. 존제여부와 권한 확인
        throwIfNotExist(lostItemId);
        throwIfNotAuthor(lostItemId,reqUserId);

        // 1. 기존 파일 불러오기
        List<TblostItemFile> existingFiles = tblostItemFileRepository.findByTblostId(lostItemId);
        Set<String> existingFileHashes = existingFiles.stream()
                .map(TblostItemFile::getFileName)
                .collect(Collectors.toSet());

        Set<TblostItemFile> filesToKeep = new HashSet<>();
        List<String> fileUrls = new ArrayList<>();

        // 2. 빈 파일 필터링 (빈 파일 제거)
        List<MultipartFile> validFiles = files.stream()
                .filter(file -> file != null && !file.isEmpty())
                .toList();


        if (!validFiles.isEmpty()) {
            // 3. 새 파일 업로드 및 중복 확인
            int fileOrder = 1;
            for (MultipartFile file : validFiles) {
                String hash = Hasher.hashFileToHex(file);
                String fileName = "LostItem/" + lostItemId + "_" + hash;

                if (existingFileHashes.contains(fileName)) {
                    // 중복된 파일이 존재할 경우 기존 파일 유지 및 순서 업데이트
                    TblostItemFile tblostItemFile = existingFiles.stream()
                            .filter(existingFile -> existingFile.getFileName().equals(fileName))
                            .findFirst()
                            .orElseThrow(() -> new NoMatchingDataException("File not found"));
                    tblostItemFile.updateFileOrder(fileOrder);
                    tblostItemFileRepository.save(tblostItemFile);
                    filesToKeep.add(tblostItemFile);
                    fileUrls.add(firebaseService.generateFileUrl(fileName));
                } else {
                    // 중복되지 않은 새 파일을 업로드 및 데이터베이스에 추가
                    String fileUrl = firebaseService.uploadFile(file, "LostItem", lostItemId, fileOrder);
                    fileUrls.add(fileUrl);
                }
                fileOrder++;
            }
        } else log.info("📍Files empty!!");


        // 4. 기존 파일 중 남아있는 파일 삭제 (중복되지 않은 파일들만 삭제)
        existingFiles.removeAll(filesToKeep);
        for (TblostItemFile fileToDelete : existingFiles) {
            firebaseService.deleteFile(fileToDelete.getFileName());
            tblostItemFileRepository.delete(fileToDelete);
        }

        // 5. 분실물 정보 업데이트
        TblostItem tblostItem = tblostItemRepository.findById(lostItemId).orElseThrow(() -> new NoMatchingDataException("Lost item not found"));
        tblostItem.update(updateServDto.getLostPersonName(), updateServDto.getContent());
        tblostItemRepository.save(tblostItem);

        return TblostItemDto.CreateResDto.builder()
                .id(tblostItem.getId())
                .fileUrls(fileUrls)
                .build();
    }

    @Override
    @Transactional
    public void deleteLostItemUser(DefaultDto.IdReqDto idReqDto, String reqUserId){
        throwIfNotExist(idReqDto.getId());
        throwIfNotAuthor(idReqDto.getId(), reqUserId);
        processDeleteLostItem(idReqDto.getId());
    }

    @Override
    @Transactional
    public void deleteLostItemAdmin(DefaultDto.IdReqDto idReqDto, String reqUserId){
        tbadminService.throwIfNotAdmin(reqUserId, Permission.ADMIN_DELETE_FEED);
        throwIfNotExist(idReqDto.getId());
        processDeleteLostItem(idReqDto.getId());
    }

    private void processDeleteLostItem(String itemId){

        // 1. 분실물 아이템에 연결된 모든 파일 불러오기
        List<TblostItemFile> itemFiles = tblostItemFileRepository.findByTblostId(itemId);

        // 2. 파일을 Firebase와 데이터베이스에서 삭제
        for (TblostItemFile file : itemFiles) {
            firebaseService.deleteFile(file.getFileName());
            tblostItemFileRepository.delete(file);
        }

        // 3. tblostItem 데이터베이스에서 삭제
        TblostItem tblostItem = tblostItemRepository.findById(itemId)
                .orElseThrow(() -> new NoMatchingDataException("삭제할 분실물 아이템이 없습니다."));
        tblostItemRepository.delete(tblostItem);
    }

    private void throwIfNotExist(String itemId){
        if (!tblostItemRepository.existsTblostItemById(itemId))
            throw new NoMatchingDataException("대상 분실물이 존제하지 않습니다.");
    }

    private void throwIfNotAuthor(String itemId, String reqUserId) {
        String authorUserId = tblostItemMapper.getAuthorIdByItemId(itemId).getId();
        if (!reqUserId.equals(authorUserId)) {
            throw new NoAuthorizationException("삭제할 권한이 없습니다.");
        }
    }

}
