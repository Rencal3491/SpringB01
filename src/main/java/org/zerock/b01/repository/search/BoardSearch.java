package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;

/*
   1. Querydsl과 기존의 JPARepository와 연동 작업 설정을 위한 인터페이스 생성
   2. 이 인터페이스를 구현하는 구현체 생성 : 주의사항-구현체의 이름은 인터페이스 +Impl 로 끝나야 함
      이름이 다를 경우 제대로 동작하지 않을 수 있음
   3. 마지막으로 BoardRepository의 선언부에서 BoardSearch 추가지정(상속)
 */
public interface BoardSearch {
    Page<Board> search1(Pageable pageable);

}
