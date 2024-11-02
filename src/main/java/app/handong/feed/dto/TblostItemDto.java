package app.handong.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
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

    @Schema(description = "분실물 상세 조회 응답 DTO")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailResDto {
        private String id;
        private String lostPersonName;
        private String content;
        private LocalDateTime createdAt;
        private List<String> fileUrls;
    }

    @Schema(description = "분실물 상세 조회 서비스 DTO")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailServDto {
        private String id;
        private String lostPersonName;
        private String content;
        private LocalDateTime createdAt;
        private List<String> fileNames;
    }

    @Schema(description = "분실물 전체 조회 서비스 DTO")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AllServDto {
        private String id;
        private String lostPersonName;
        private String content;
        private LocalDateTime createdAt;
        private String fileNames;
    }

}
