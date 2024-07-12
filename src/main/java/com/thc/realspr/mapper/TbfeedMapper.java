package com.thc.realspr.mapper;

import com.thc.realspr.dto.TbfeedDto;

import java.util.List;

public interface TbfeedMapper {
    TbfeedDto.SelectResDto detail(String id);

    List<TbfeedDto.SelectResDto> getAll();
}
