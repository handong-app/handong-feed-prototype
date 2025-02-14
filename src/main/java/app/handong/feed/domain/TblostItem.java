package app.handong.feed.domain;

import app.handong.feed.dto.TblostItemDto;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(indexes = {
        @Index(columnList = "deleted"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@Entity
public class TblostItem extends AuditingFields {
    private String userId;
    private String lostPersonName;
    private String content;

    protected TblostItem() {}
    public TblostItem(String userId, String lostPersonName, String content) {
        this.userId = userId;
        this.lostPersonName = lostPersonName;
        this.content = content;
    }
    public static TblostItem of(String userId, String lostPersonName, String content) {
        return new TblostItem(userId, lostPersonName, content);
    }

    public void update(String lostPersonName, String content) {
        this.lostPersonName = lostPersonName;
        this.content = content;
    }

    public TblostItemDto.CreateResDto toCreateResDto() {
        return TblostItemDto.CreateResDto.builder().id(this.getId()).build();
    }
}
