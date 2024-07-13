package com.thc.realspr.dto;

import com.thc.realspr.domain.Tbfeed;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TbfeedDto {

    @Schema
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReqDto {
        @Schema(description = "title", example = "제목")
        @NotNull
        @NotEmpty
        @Size(max = 100)
        private String title;

        @Schema(description = "content", example = "내용")
        @NotNull
        @NotEmpty
        @Size(max = 2000000)
        private String content;

        @Schema(description = "author", example = "저자")
        @NotNull
        @NotEmpty
        @Size(max = 100)
        private String author;

        public Tbfeed toEntity() {
            return Tbfeed.of(title, content, author);
        }
    }

    @Schema
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateResDto {
        @Schema(description = "id", example = "length32textnumber")
        private String id;
    }

    @Schema
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectResDto {
        @Schema(description = "id", example = "")
        private String id;
        @Schema(description = "title", example = "")
        private String title;
        @Schema(description = "content", example = "")
        private String content;
        @Schema(description = "author", example = "")
        private String author;
        @Schema(description = "createdAt", example = "")
        private String createdAt;
        @Schema(description = "modifiedAt", example = "")
        private String modifiedAt;
    }

// list 다중 필터 기능임
//    @Schema
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class ListReqDto {
//        @Schema(description = "id", example = "")
//        private String id;
//        @Schema(description = "title", example = "")
//        private String title;
//        @Schema(description = "content", example = "")
//        private String content;
//        @Schema(description = "author", example = "")
//        private String author;
//        @Schema(description = "phone", example = "")
//        private String phone;
//        @Schema(description = "createdAt", example = "")
//        private String createdAt;
//        @Schema(description = "modifiedAt", example = "")
//        private String modifiedAt;
//    }

}
