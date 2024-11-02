//package com.thc.realspr.domain;
//
//import com.thc.realspr.dto.TbInfoEntryDto;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//
//@Entity
//@Getter
//@Setter
//@EntityListeners(AuditingEntityListener.class)
//public class TbInfoEntry {
//    @Id // 기본 키로 설정
//    @JoinColumn(name = "tbInfo_id")
//    private Integer tbInfo_id;
//
//    @Column
//    private String category; // 카테고리
//
//    @Column
//    private String InfoName; // 이름
//
//    @Column
//    private Integer orderInfoShow; // 이름 보이는 순서
//
//    @Column
//    private Integer orderCategoryShow; // 카테고리 보이는 순서
//
//    @CreatedDate
//    @Column(updatable = false)
//    private LocalDateTime createdAt; // 생성 시간
//
//    @LastModifiedDate
//    private LocalDateTime modifiedAt; // 수정 시간
//
//
//
//    // 기본 생성자
//    protected TbInfoEntry() {
//    }
//
//
//    public static TbInfoEntry of(Integer tbInfo_id, String category, String InfoName, Integer orderInfoShow, Integer orderCategoryShow, LocalDateTime createdAt, LocalDateTime modifiedAt) {
//        return new TbInfoEntry(tbInfo_id, category, InfoName, orderInfoShow, orderCategoryShow, createdAt, modifiedAt);
//    }
//
//
//    // 생성자
//    public TbInfoEntry(Integer tbInfo_id, String category, String InfoName, Integer orderInfoShow, Integer orderCategoryShow, LocalDateTime createdAt, LocalDateTime modifiedAt) {
//        this.tbInfo_id = tbInfo_id;
//        this.category = category;
//        this.InfoName = InfoName;
//        this.orderInfoShow = orderInfoShow;
//        this.orderCategoryShow = orderCategoryShow;
//        this.createdAt = createdAt;
//        this.modifiedAt = modifiedAt;
//    }
//
//    public TbInfoEntryDto.InfoEntryCreateReqDto toCreateResDto() {
//        return TbInfoEntryDto.InfoEntryCreateReqDto.builder().tbInfo_id(this.getTbInfo_id()).build();
//    }
//}
