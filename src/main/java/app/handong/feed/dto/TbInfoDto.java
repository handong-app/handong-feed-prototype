package app.handong.feed.dto;

import app.handong.feed.domain.TbInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class TbInfoDto {
        @Builder
        @Schema
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class InfoCreateReqDto {
            @Schema(description = "게시판 종류", example = "101")
            @NotNull
            private Integer tbInfoId;

            @Schema(description = "고유 식별 아이디", example = "12345")
            @NotNull
            private String uid;

            @Schema(description = "운영일", example = "Monday to Friday")
            private String openDay;

            @Schema(description = "운영시간", example = "09:00-18:00")
            private String openTime;

            @Schema(description = "휴식시간", example = "12:00-13:00")
            private String breakTime;

            @Schema(description = "휴무일", example = "Sunday")
            private String breakDay;

            @Schema(description = "기타 정보", example = "No holidays")
            private String etc;

            @Schema(description = "생성 시간", example = "2024-10-30T01:13:03")
            private LocalDateTime createdAt;

            @Schema(description = "수정 유지 이름", example = "Admin")
            private String username;

            public TbInfo toEntity() {
                return TbInfo.of(
                        this.tbInfoId,
                        this.uid,
                        this.openDay,
                        this.openTime,
                        this.breakTime,
                        this.breakDay,
                        this.etc,
                        this.username,
                        this.createdAt
                );
            }
        }



        @Builder
        @Schema
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class InfoResponseDto {
            @Schema(description = "게시판 종류", example = "101")
            private Integer tbInfoId;

            @Schema(description = "고유 식별 아이디", example = "12345")
            private String uid;

            @Schema(description = "운영일", example = "Monday to Friday")
            private String openDay;

            @Schema(description = "운영시간", example = "09:00-18:00")
            private String openTime;

            @Schema(description = "휴식시간", example = "12:00-13:00")
            private String breakTime;

            @Schema(description = "휴무일", example = "Sunday")
            private String breakDay;

            @Schema(description = "기타 정보", example = "No holidays")
            private String etc;

            @Schema(description = "생성 시간", example = "2024-10-30T01:13:03")
            private LocalDateTime createdAt;

            @Schema(description = "수정 유지 이름", example = "Admin")
            private String username;


        }


    }
