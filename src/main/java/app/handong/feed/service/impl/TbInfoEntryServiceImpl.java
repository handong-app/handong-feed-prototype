package app.handong.feed.service.impl;


import app.handong.feed.domain.TbInfoEntry;
import app.handong.feed.dto.TbInfoEntryDto;
import app.handong.feed.repository.TbInfoEntryRepository;
import app.handong.feed.service.TbInfoEntryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TbInfoEntryServiceImpl implements TbInfoEntryService {



    @Autowired
    private final TbInfoEntryRepository tbInfoEntryRepository;



    public TbInfoEntryServiceImpl(TbInfoEntryRepository tbInfoEntryRepository) {
        this.tbInfoEntryRepository = tbInfoEntryRepository;
    }


    @Override
    public TbInfoEntryDto.InfoEntryCreateReqDto createInfo(TbInfoEntryDto.InfoEntryCreateReqDto param){
        TbInfoEntry savedEntity = tbInfoEntryRepository.save(param.toEntity());
        return new TbInfoEntryDto.InfoEntryCreateReqDto(savedEntity);
    }



    @Override
    public TbInfoEntryDto.InfoEntryCreateReqDto updateInfo(int tbInfoId,TbInfoEntryDto.InfoEntryCreateReqDto param) {
        TbInfoEntry tbInfoEntry = tbInfoEntryRepository.findById(tbInfoId)
                .orElseThrow(() -> new EntityNotFoundException("No data found with tbInfoId: " + param.getTbInfoId()));

        // Update fields
        tbInfoEntry.setInfoName(param.getInfoName());
        tbInfoEntry.setCategory(param.getCategory());
        tbInfoEntry.setOrderInfoShow(param.getOrderInfoShow());
        tbInfoEntry.setOrderCategoryShow(param.getOrderCategoryShow());
        tbInfoEntry.setModifiedAt(LocalDateTime.now());

        TbInfoEntry updatedEntity = tbInfoEntryRepository.save(tbInfoEntry);

        return TbInfoEntryDto.InfoEntryCreateReqDto.builder()
                .tbInfoId(updatedEntity.getTbInfoId())
                .infoName(updatedEntity.getInfoName())
                .category(updatedEntity.getCategory())
                .orderInfoShow(updatedEntity.getOrderInfoShow())
                .orderCategoryShow(updatedEntity.getOrderCategoryShow())
                .createdAt(updatedEntity.getCreatedAt())
                .modifiedAt(updatedEntity.getModifiedAt())
                .build();
    }





}