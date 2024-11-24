package app.handong.feed.mapper;

import app.handong.feed.dto.DefaultDto;
import app.handong.feed.dto.TblostItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface TblostItemMapper {
    TblostItemDto.DetailServDto getLostItemDetailById(@Param("itemId") String itemId);
    List<TblostItemDto.DetailServDto> getAllLostItems();
    DefaultDto.IdResDto getAuthorIdByItemId(@Param("itemId") String itemId);
}
