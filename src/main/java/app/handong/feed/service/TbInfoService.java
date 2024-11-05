package app.handong.feed.service;



import app.handong.feed.dto.TbInfoDto;
import org.springframework.stereotype.Service;

@Service
public interface TbInfoService {
    public TbInfoDto.InfoCreateReqDto createInfo(TbInfoDto.InfoCreateReqDto param);
    public TbInfoDto.InfoCreateReqDto getInfo_Id(String uid);
    public TbInfoDto.InfoCreateReqDto updateInfo(String uid, TbInfoDto.InfoCreateReqDto param);


}
