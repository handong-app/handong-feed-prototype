package com.thc.realspr.service;

import com.thc.realspr.domain.Tbfeed;
import com.thc.realspr.dto.TbfeedDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TbfeedService {
    //    public Map<String, Object> create(Map<String, Object> param);
    public Map<String, Object> update(Map<String, Object> param);

    //    public Map<String, Object> get(String id);
    public void delete(String id);
//    public List<Map<String, Object>> getAll();

    public TbfeedDto.CreateResDto create(TbfeedDto.CreateReqDto param);

    public TbfeedDto.SelectResDto get(String id);

    //    public List<TbfeedDto.SelectResDto> list(TbfeedDto.ListReqDto param);
    public List<TbfeedDto.SelectResDto> getAll();
}
