package app.handong.feed.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(indexes = {
        @Index(columnList = "deleted"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@Entity
public class TblostItemFile extends AuditingFields {
    private String tblostId;
    private String fileName;
    private String fileType;

    protected TblostItemFile() {}
    public TblostItemFile(String lostId, String fileName, String fileType) {
        this.tblostId = lostId;
        this.fileName = fileName;
        this.fileType = fileType;
    }
    public static TblostItemFile of(String lostId, String fileName, String fileType) {
        return new TblostItemFile(lostId, fileName, fileType);
    }
}
