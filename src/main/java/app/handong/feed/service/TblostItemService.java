package app.handong.feed.service;

import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface TblostItemService {

    TblostItemDto.CreateResDto createLostItem(TblostItemDto.CreateReqDto createReqDto, List<MultipartFile> files, String reqUserId);
    TblostItemDto.DetailResDto getLostItemDetail(DefaultDto.IdReqDto idReqDto);
    List<TblostItemDto.DetailResDto> getAllLostItems();
    TblostItemDto.CreateResDto updateLostItem(TblostItemDto.UpdateServDto updateServDto, List<MultipartFile> files, String reqUserId);
    void deleteLostItemUser(DefaultDto.IdReqDto idReqDto, String reqUserId);
    void deleteLostItemAdmin(DefaultDto.IdReqDto idReqDto, String reqUserId);
}
