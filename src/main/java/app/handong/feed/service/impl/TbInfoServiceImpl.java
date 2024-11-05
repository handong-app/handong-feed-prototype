package app.handong.feed.service.impl;



import app.handong.feed.domain.TbInfo;
import app.handong.feed.dto.TbInfoDto;
import app.handong.feed.repository.TbInfoRepository;
import app.handong.feed.service.TbInfoService;
import jakarta.persistence.EntityNotFoundException;
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
        TbInfo savedInfo = tbInfoRepository.save(param.toEntity());
        return savedInfo.toCreateResDto();
    }

    @Override
    public TbInfoDto.InfoCreateReqDto getInfo_Id(String uid){
        return tbInfoRepository.findByUid(uid).toCreateResDto();
    }

    @Override
    public TbInfoDto.InfoCreateReqDto updateInfo(String uid, TbInfoDto.InfoCreateReqDto param) {

        TbInfo existingInfo = tbInfoRepository.findByUid(uid);
        if (existingInfo == null) {
            throw new EntityNotFoundException("UID가 " + uid + "인 정보를 찾을 수 없습니다.");
        }


        existingInfo.updateFields(param);


        TbInfo updatedInfo = tbInfoRepository.save(existingInfo);


        return updatedInfo.toCreateResDto();
    }


}
