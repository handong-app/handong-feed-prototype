package com.thc.realspr.service.impl;

import com.thc.realspr.domain.Tbfeed;
import com.thc.realspr.domain.Tbuser;
import com.thc.realspr.dto.TbfeedDto;
import com.thc.realspr.mapper.TbfeedMapper;
import com.thc.realspr.repository.TbfeedRepository;
import com.thc.realspr.repository.TbuserRepository;
import com.thc.realspr.service.TbfeedService;
import com.thc.realspr.service.TbuserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbfeedServiceImpl implements TbfeedService {

    private final TbfeedRepository tbfeedRepository;
    private final TbfeedMapper tbfeedMapper;

    public TbfeedServiceImpl(
            TbfeedRepository tbfeedRepository,
            TbfeedMapper tbfeedMapper
    ) {
        this.tbfeedRepository = tbfeedRepository;
        this.tbfeedMapper = tbfeedMapper;
    }

    public TbfeedDto.CreateResDto create(TbfeedDto.CreateReqDto param) {
        return tbfeedRepository.save(param.toEntity()).toTbfeedAfterCreateDto();
    }

    public void delete(String id) {
        Tbfeed tbfeed = tbfeedRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        tbfeed.setDeleted("Y");
        tbfeedRepository.save(tbfeed);

    }

    public Map<String, Object> update(Map<String, Object> param) {
//        Map<String, Object> returnMap = new HashMap<String, Object>();
//        System.out.println(param);
//        Tbuser tbuser = tbuserRepository.findById(param.get("id") + "").orElseThrow(() -> new RuntimeException(""));
//        if(param.get("name") != null) {
//            tbuser.setName(param.get("name") + "");
//        }
//        if(param.get("nick") != null) {
//            tbuser.setNick(param.get("nick") + "");
//        }
//        if(param.get("phone") != null) {
//            tbuser.setPhone(param.get("phone") + "");
//        }
//
//        tbuserRepository.save(tbuser);
//        returnMap.put("id", tbuser.getId());
//        return returnMap;
        return null;
    }

    public TbfeedDto.SelectResDto get(String id) {
        TbfeedDto.SelectResDto selectDto = tbfeedMapper.detail(id);

        return selectDto;
    }

    //    public List<TbfeedDto.SelectResDto> list(TbfeedDto.ListReqDto param) {
////        List<Tbfeed> allFeed  = tbfeedRepository.findAll();
////        List<Tbfeed> allFeed = tbfeedRepository.findByDeletedNot("Y");
//
//        List<TbfeedDto.SelectResDto> list = tbfeedMapper.list(param);
//        List<TbfeedDto.SelectResDto> newlist = new ArrayList<>();
//        return newlist;
////        for (TbfeedDto.SelectResDto tbfeedSelectDto : list) {
////            newlist.add(get(tbfeedSelectDto.getId()));
////        }
////        return newlist;
//    }
    public List<TbfeedDto.SelectResDto> getAll() {
//        List<Tbfeed> allFeed  = tbfeedRepository.findAll();
//        List<Tbfeed> allFeed = tbfeedRepository.findByDeletedNot("Y");

        List<TbfeedDto.SelectResDto> list = tbfeedMapper.getAll();
        List<TbfeedDto.SelectResDto> newlist = new ArrayList<>();
//        return newlist;
        for (TbfeedDto.SelectResDto tbfeedSelectDto : list) {
            newlist.add(get(tbfeedSelectDto.getId()));
        }
        return newlist;
    }
}
