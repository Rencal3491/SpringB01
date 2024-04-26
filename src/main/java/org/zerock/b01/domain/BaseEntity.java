package org.zerock.b01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
    BaseEntity에 설정할 내용 -  모든 테이블에서 공통적으로 사용하는 컬럼 작성
    @MappedSuperClass 를 사용하여 공통 속성 적용
    공통으로 사용되는 컬럼을 지정하여 해당 클래스를 상속하여 사용할 수 있음
 */
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class}) //@EnableJpaAuditing 메인에 설정해야함
@Getter
abstract class BaseEntity {
    //시간 설정 -  등록, 수정시간 설정

    @CreatedDate //생성날짜
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate //마지막 수정날짜
    @Column(name = "moddate")
    private LocalDateTime modDate;

}
