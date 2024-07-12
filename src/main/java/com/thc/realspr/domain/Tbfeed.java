package com.thc.realspr.domain;

import com.thc.realspr.dto.TbfeedDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) // For Audit
public class Tbfeed {
    @Id private String id;
    @Setter @Column(nullable = false) private String deleted; // 삭제여부

    @Setter @Column(nullable = false) private String title; // 사용자아이디
    @Setter @Column(nullable = false) private String author; // 비번
    @Setter @Column(nullable = false, length = 2000000) @Lob private String content; // 본문

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    protected Tbfeed() {
    }

    private Tbfeed(String title, String author, String content) {
        this.title = title;
        this.author = author;
//        this.date = date;
        this.content = content;
    }

    public static Tbfeed of(String title, String author, String content) {
        return new Tbfeed(title, author, content);
    }

    @PrePersist
    public void onPrePersist() {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.deleted = "N";
    }

    public TbfeedDto.CreateResDto toTbfeedAfterCreateDto() {
        return TbfeedDto.CreateResDto.builder().id(getId()).build();
    }
}
