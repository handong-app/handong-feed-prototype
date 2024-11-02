package app.handong.feed.dto;

import app.handong.feed.domain.TblostItemFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class TblostItemFileDto {
    @Schema(description = "분실물 파일정보 저장 서비스 DTO")
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateServDto {
        private String tblostId;
        private String fileName;
        private String fileType;
        private Integer fileOrder;

        public TblostItemFile toEntity() {
            return TblostItemFile.of(tblostId, fileName, fileType, fileOrder);
        }
    }


}
