package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity //엔티티 선언
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {
    /*
     Identity - 데이터베이스에 위임 (Auto Increment 와 비슷)
     Sequence - 데이터베이스의 시퀀스 오브젝트 사용 - @SequenceGenerator 필요함
     Table - 키 생성용 테이블 사용, 모든 db에서 사용가능함 - @TableGenerator 필요함
     Auto - 방언(sql)에 따라서 자동 지정됨, 기본값
     UUID - 랜덤 아이디 생성 - 16진수
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto value generate
    private Long bno;

    @Column(length = 500, nullable = false) //칼럼의 길이와 null 허용여부
    private  String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private  String writer;



}
