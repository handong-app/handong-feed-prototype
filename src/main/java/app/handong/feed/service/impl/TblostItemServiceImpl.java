package app.handong.feed.service.impl;

import app.handong.feed.domain.TblostItem;
import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemDto;
import app.handong.feed.mapper.TblostItemMapper;
import app.handong.feed.repository.TblostItemRepository;
import app.handong.feed.service.FirebaseService;
import app.handong.feed.service.TblostItemService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class TblostItemServiceImpl implements TblostItemService {

    private final TblostItemRepository tblostRepository;
    private final TblostItemMapper tblostItemMapper;
    private final FirebaseService firebaseService;

    public TblostItemServiceImpl(TblostItemRepository tblostRepository, TblostItemMapper tblostItemMapper, FirebaseService firebaseService) {
        this.tblostRepository = tblostRepository;
        this.tblostItemMapper = tblostItemMapper;
        this.firebaseService = firebaseService;
    }

    @Override
    @Transactional
    public TblostItemDto.CreateResDto createLostItem(TblostItemDto.CreateReqDto createReqDto, List<MultipartFile> files, String reqUserId) {
        TblostItem tblostItem = TblostItem.of(reqUserId, createReqDto.getLostPersonName(), createReqDto.getContent());
        TblostItem savedLostItem = tblostRepository.save(tblostItem);

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
}
