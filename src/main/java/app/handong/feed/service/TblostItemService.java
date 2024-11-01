package app.handong.feed.service;

import app.handong.feed.dto.TblostItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface TblostItemService {

    TblostItemDto.CreateResDto createLostItem(TblostItemDto.CreateReqDto createReqDto, List<MultipartFile> files, String reqUserId);

}
