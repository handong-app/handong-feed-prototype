package app.handong.feed.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class UserInteractionDto {
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeenSubjectReqDto {
        @Schema(description = "title", example = "")
        private int subjectId;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadAllReqDto {
        @Schema(description = "lastSentAt", example = "")
        private int lastSentAt;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        @Schema(description = "title", example = "")
        private int subjectId;
        private String id;
        private int sentAt;
        private String message;
        private List<String> files;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileDetail {
        @Schema(description = "title", example = "")
        private String hash;
        private String ext;
    }
}
