package app.handong.feed.service;



import app.handong.feed.dto.TbInfoEntryDto;
import org.springframework.stereotype.Service;

@Service
public interface TbInfoEntryService {
    public TbInfoEntryDto.InfoEntryCreateReqDto createInfo(TbInfoEntryDto.InfoEntryCreateReqDto param);


    public TbInfoEntryDto.InfoEntryCreateReqDto updateInfo(int tbInfoId,TbInfoEntryDto.InfoEntryCreateReqDto param);
}
