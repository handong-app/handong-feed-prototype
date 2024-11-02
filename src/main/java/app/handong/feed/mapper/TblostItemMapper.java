package app.handong.feed.mapper;

import app.handong.feed.dto.TblostItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface TblostItemMapper {
    TblostItemDto.DetailServDto getLostItemDetailById(@Param("itemId") String itemId);
}
