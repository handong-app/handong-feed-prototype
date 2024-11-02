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
        List<String> fileUrls = detail.getFileNames().stream()
                .map(firebaseService::generateFileUrl)
                .toList();

        return TblostItemDto.DetailResDto.builder()
                .id(detail.getId())
                .lostPersonName(detail.getLostPersonName())
                .content(detail.getContent())
                .createdAt(detail.getCreatedAt())
                .fileUrls(fileUrls)
                .build();
    }

    @Override
    public List<TblostItemDto.DetailResDto> getAllLostItems() {
        List<TblostItemDto.AllServDto> lostItems = tblostItemMapper.getAllLostItems();


        return lostItems.stream()
                .map(item -> {
                    log.info("{}",item.getFileNames());
                    List<String> fileUrls = Arrays.stream(item.getFileNames().split(","))
                            .map(fileName -> {
                                log.info("{}", fileName);
                                return firebaseService.generateFileUrl(fileName);
                            })
                            .toList();

                    return TblostItemDto.DetailResDto.builder()
                            .id(item.getId())
                            .lostPersonName(item.getLostPersonName())
                            .content(item.getContent())
                            .createdAt(item.getCreatedAt())
                            .fileUrls(fileUrls)
                            .build();
                })
                .toList();
    }
}
