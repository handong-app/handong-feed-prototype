package app.handong.feed.service;



import app.handong.feed.dto.TbInfoEntryDto;
import org.springframework.stereotype.Service;

@Service
public interface TbInfoEntryService {
    public TbInfoEntryDto.InfoEntryCreateReqDto createInfo(TbInfoEntryDto.InfoEntryCreateReqDto param);
    public TbInfoEntryDto.InfoEntryCreateReqDto readInfo(int tbinfoid);

    public TbInfoEntryDto.InfoEntryCreateReqDto updateInfo(TbInfoEntryDto.InfoEntryCreateReqDto param);
}
