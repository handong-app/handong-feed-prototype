package app.handong.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class TblostItemDto {
    @Schema(description = "분실물 게시 요청 DTO")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReqDto {
        private String lostPersonName;
        private String content;
    }

    @Schema(description = "분실물 게시 응답 DTO")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResDto {
        private String id;
        private List<String> fileUrls;
    }


}
