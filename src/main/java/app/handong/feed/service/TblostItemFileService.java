package app.handong.feed.service;


import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemFileDto;
import org.springframework.stereotype.Service;

@Service
public interface TblostItemFileService {
    DefaultDto.IdResDto createLostItemFile(TblostItemFileDto.CreateServDto createServDto);

}
