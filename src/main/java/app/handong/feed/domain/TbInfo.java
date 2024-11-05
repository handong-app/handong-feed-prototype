package app.handong.feed.domain;


import app.handong.feed.dto.TbInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class) // For Audit
public class TbInfo {


    @Id
    private String uid; // 고유 식별 아이디

    @Column
    private Integer tbInfoId;

    @Column
    private String openDay; // 운영일

    @Column
    private String openTime; // 운영시간

    @Column
    private String breakTime; // 휴식시간

    @Column
    private String breakDay; // 휴무일

    @Column
    private String etc; // 기타 정보

    @Column
    private  String username; // 생성 유저 이름

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성 시간



    // 기본 생성자
    protected TbInfo() {
    }

    // 생성자
    public TbInfo(Integer tbInfoId, String uid, String openDay, String openTime, String breakTime, String breakDay, String etc, String username, LocalDateTime createdAt) {
        this.tbInfoId = tbInfoId;
        this.uid = uid;
        this.openDay = openDay;
        this.openTime = openTime;
        this.breakTime = breakTime;
        this.breakDay = breakDay;
        this.etc = etc;
        this.username = username;
        this.createdAt = createdAt;
    }

    public void updateFields(TbInfoDto.InfoCreateReqDto dto) {
        this.openDay = dto.getOpenDay();
        this.openTime = dto.getOpenTime();
        this.breakTime = dto.getBreakTime();
        this.breakDay = dto.getBreakDay();
        this.etc = dto.getEtc();
        this.username = dto.getUsername();
        this.createdAt = dto.toEntity().getCreatedAt();
    }



    public static TbInfo of(Integer tbInfoId, String uid, String openDay, String openTime, String breakTime, String breakDay, String etc, String username, LocalDateTime createdAt) {
        return new TbInfo(tbInfoId, uid, openDay, openTime, breakTime, breakDay, etc, username, createdAt);
    }

    @PrePersist
    public void onPrePersist() {
        if (this.uid == null || this.uid.isEmpty()) {
            this.uid = UUID.randomUUID().toString().replace("-", "");
        }
    }

    public TbInfoDto.InfoCreateReqDto toCreateResDto() {
        return TbInfoDto.InfoCreateReqDto.builder()
                .tbInfoId(this.tbInfoId)
                .uid(this.uid)
                .openDay(this.openDay)
                .openTime(this.openTime)
                .breakTime(this.breakTime)
                .breakDay(this.breakDay)
                .etc(this.etc)
                .username(this.username)
                .createdAt(this.createdAt)
                .build();
    }
}
