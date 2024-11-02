package app.handong.feed.service.impl;

import app.handong.feed.domain.TblostItem;
import app.handong.feed.domain.TblostItemFile;
import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemDto;
import app.handong.feed.exception.NoMatchingDataException;
import app.handong.feed.mapper.TblostItemMapper;
import app.handong.feed.repository.TblostItemFileRepository;
import app.handong.feed.repository.TblostItemRepository;
import app.handong.feed.service.FirebaseService;
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

    public TblostItemServiceImpl(TblostItemRepository tblostRepository, TblostItemFileRepository tblostItemFileRepository, TblostItemMapper tblostItemMapper, FirebaseService firebaseService) {
        this.tblostItemRepository = tblostRepository;
        this.tblostItemFileRepository = tblostItemFileRepository;
        this.tblostItemMapper = tblostItemMapper;
        this.firebaseService = firebaseService;
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
            throw new NoSuchElementException("해당 분실물 없음");
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
        String lostId = updateServDto.getId();

        // 1. 기존 파일 불러오기
        List<TblostItemFile> existingFiles = tblostItemFileRepository.findByTblostId(lostId);
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
                String fileName = "LostItem/" + lostId + "_" + hash;

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
                    String fileUrl = firebaseService.uploadFile(file, "LostItem", lostId, fileOrder);
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
        TblostItem tblostItem = tblostItemRepository.findById(lostId).orElseThrow(() -> new NoMatchingDataException("Lost item not found"));
        tblostItem.update(updateServDto.getLostPersonName(), updateServDto.getContent());
        tblostItemRepository.save(tblostItem);

        return TblostItemDto.CreateResDto.builder()
                .id(tblostItem.getId())
                .fileUrls(fileUrls)
                .build();
    }
}
