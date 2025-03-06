package app.handong.feed.domain;

import app.handong.feed.id.UserSubjectId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) // For Audit
public class TbUserReadAll {
    @Id
    @Setter @Column(nullable = false) private String id; // 사용자아이디
    @Setter @Column(nullable = false, name = "read_last_sent_at") private int readLastSentAt;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    protected TbUserReadAll() {}

    public TbUserReadAll(String id, int readLastSentAt) {
        this.id = id;
        this.readLastSentAt = readLastSentAt;
    }

    @PrePersist
    public void onPrePersist() {

    }
}
