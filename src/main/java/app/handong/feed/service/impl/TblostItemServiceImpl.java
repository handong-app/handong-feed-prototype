package app.handong.feed.service.impl;

import app.handong.feed.domain.TblostItem;
import app.handong.feed.dto.TblostItemDto;
import app.handong.feed.repository.TblostItemRepository;
import app.handong.feed.service.FirebaseStorageService;
import app.handong.feed.service.TblostItemService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TblostItemServiceImpl implements TblostItemService {

    private final TblostItemRepository tblostRepository;
    private final FirebaseStorageService firebaseStorageService;

    public TblostItemServiceImpl(TblostItemRepository tblostRepository, FirebaseStorageService firebaseStorageService) {
        this.tblostRepository = tblostRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional
    public TblostItemDto.CreateResDto createLostItem(TblostItemDto.CreateReqDto createReqDto, List<MultipartFile> files, String reqUserId) {
        TblostItem tblostItem = TblostItem.of(reqUserId, createReqDto.getLostPersonName(), createReqDto.getContent());
        TblostItem savedLostItem = tblostRepository.save(tblostItem);

        List<String> fileUrls = firebaseStorageService.uploadFiles(files, "LostItem", savedLostItem.getId());

        return TblostItemDto.CreateResDto.builder()
                .id(savedLostItem.getId())
                .fileUrls(fileUrls)
                .build();
    }
}
