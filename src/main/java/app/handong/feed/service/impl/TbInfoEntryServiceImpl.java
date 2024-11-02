package app.handong.feed.service.impl;//package com.thc.realspr.service.impl;
//
//import com.thc.realspr.domain.TbInfoEntry;
//import com.thc.realspr.dto.TbInfoEntryDto;
//import com.thc.realspr.repository.TbInfoEntryRepository;
//import com.thc.realspr.repository.TbInfoRepository;
//import com.thc.realspr.service.TbInfoEntryService;
//import com.thc.realspr.service.TbInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class TbInfoEntryServiceImpl implements TbInfoEntryService {
//
//
//
//    @Autowired
//    private final TbInfoEntryRepository tbInfoEntryRepository;
//
//
//
//    public TbInfoEntryServiceImpl(TbInfoEntryRepository tbInfoEntryRepository) {
//        this.tbInfoEntryRepository = tbInfoEntryRepository;
//    }
//
//
//    @Override
//    public TbInfoEntryDto.InfoEntryCreateReqDto createInfo(TbInfoEntryDto.InfoEntryCreateReqDto param){
//
//        return tbInfoEntryRepository.save(param.toEntity()).toCreateResDto();
//    }
//
//    @Override
//    public TbInfoEntryDto.InfoEntryCreateReqDto updateInfo(TbInfoEntryDto.InfoEntryCreateReqDto param) {
//        // findByTbInfoId 메서드가 Optional이 아닌 경우, null 체크로 존재 여부 확인
//        TbInfoEntry tbInfoEntry = tbInfoEntryRepository.findBytbInfo_id(param.getTbInfo_id());
//
//        if (tbInfoEntry == null) {
//            throw new RuntimeException("No data found with tbInfo_id: " + param.getTbInfo_id());
//        }
//        // 현재 시간을 modifiedAt에 설정
//        tbInfoEntry.setModifiedAt(LocalDateTime.now());
//
//        // 변경 사항 저장
//        TbInfoEntry updatedEntity = tbInfoEntryRepository.save(tbInfoEntry);
//
//        // 업데이트된 엔티티를 DTO로 변환하여 반환
//        return new TbInfoEntryDto.InfoEntryCreateReqDto(updatedEntity);
//    }
//
//
//
//
//
//}