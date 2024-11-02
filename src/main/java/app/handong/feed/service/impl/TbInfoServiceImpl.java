package app.handong.feed.service.impl;



import app.handong.feed.dto.TbInfoDto;
import app.handong.feed.repository.TbInfoRepository;
import app.handong.feed.service.TbInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbInfoServiceImpl implements TbInfoService {

    private final TbInfoRepository tbInfoRepository;
//    private final TbInfoEntryRepository tbInfoEntryRepository;
//    private final TbInfoEntryService tbInfoEntryService;

    @Autowired
    public TbInfoServiceImpl(TbInfoRepository tbInfoRepository) {
        this.tbInfoRepository = tbInfoRepository;
//        this.tbInfoEntryRepository = tbInfoEntryRepository;
//        this.tbInfoEntryService = tbInfoEntryService;
    }

    @Override
    public TbInfoDto.InfoCreateReqDto createInfo(TbInfoDto.InfoCreateReqDto param){
//        return tbInfoRepository.save(param.toEntity()).toCreateResDto();
        return tbInfoRepository.save(param.toEntity()).toCreateResDto();
    }

    @Override
    public TbInfoDto.InfoCreateReqDto getInfo_Id(String uid){
        return tbInfoRepository.findByUid(uid).toCreateResDto();
    }
}
