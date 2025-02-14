package app.handong.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class) // 해당 클래스에 Auditing 기능을 포함
@MappedSuperclass
public abstract class AuditingFields {

    @Id
    private String id;

    @Column(nullable = false)
    @Setter
    protected String deleted;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    protected LocalDateTime modifiedAt;

    @PrePersist
    public void onPrePersist() {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.deleted = "N";
    }
}
