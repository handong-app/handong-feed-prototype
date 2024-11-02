//package com.thc.realspr.dto;
//
//import com.thc.realspr.domain.TbInfoEntry;
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//public class TbInfoEntryDto {
//
//
//        @Builder
//        @Schema
//        @Getter
//        @Setter
//        @AllArgsConstructor
//        @NoArgsConstructor
//        public static class InfoEntryCreateReqDto {
//            @Schema(description = "board의 FK", example = "101")
//            @NotNull
//            private Integer tbInfo_id;
//
//            @Schema(description = "카테고리", example = "General")
//            private String category;
//
//            @Schema(description = "이름", example = "Notice Board")
//            private String InfoName;
//
//            @Schema(description = "이름 보이는 순서", example = "1")
//            private Integer orderInfoShow;
//
//            @Schema(description = "카테고리 보이는 순서", example = "2")
//            private Integer orderCategoryShow;
//
//            @Schema(description = "최초 생성 시간", example = "2024-10-30T01:13:03")
//            private LocalDateTime createdAt;
//
//            @Schema(description = "최근 수정 시간", example = "2024-10-31T01:13:03")
//            private LocalDateTime modifiedAt;
//
//            public InfoEntryCreateReqDto(TbInfoEntry tbInfoEntry) {
//                this.tbInfo_id = tbInfoEntry.getTbInfo_id();
//                this.category = tbInfoEntry.getCategory();
//                this.InfoName = tbInfoEntry.getInfoName();
//                this.orderInfoShow = tbInfoEntry.getOrderInfoShow();
//                this.orderCategoryShow = tbInfoEntry.getOrderCategoryShow();
//                this.createdAt = tbInfoEntry.getCreatedAt();
//                this.modifiedAt = tbInfoEntry.getModifiedAt();
//            }
//            public TbInfoEntry toEntity() {
//                return TbInfoEntry.of(
//                        this.tbInfo_id,
//                        this.category,
//                        this.InfoName,
//                        this.orderInfoShow,
//                        this.orderCategoryShow,
//                        this.createdAt,
//                        this.modifiedAt
//                );
//            }
//
//        }
//
//
//        @Builder
//        @Schema
//        @Getter
//        @Setter
//        @AllArgsConstructor
//        @NoArgsConstructor
//        public static class ShowInfoResponseDto {
//            @Schema(description = "board의 외래 키", example = "101")
//            private Integer tbInfo_id;
//
//            @Schema(description = "카테고리", example = "General")
//            private String category;
//
//            @Schema(description = "이름", example = "Notice Board")
//            private String boardName;
//
//            @Schema(description = "이름 보이는 순서", example = "1")
//            private Integer orderBoardShow;
//
//            @Schema(description = "카테고리 보이는 순서", example = "2")
//            private Integer orderCategoryShow;
//
//            @Schema(description = "최초 생성 시간", example = "2024-10-30T01:13:03")
//            private LocalDateTime createdAt;
//
//            @Schema(description = "최근 수정 시간", example = "2024-10-31T01:13:03")
//            private LocalDateTime modifiedAt;
//
//
//        }
//    }
