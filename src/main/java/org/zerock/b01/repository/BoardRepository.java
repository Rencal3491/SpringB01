package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Board;

//                                  <Board, Long> - 엔티티 타입 ,  id

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    //@Query 어노테이션에서 사용하는 구문은 JPQL을 사용
    //JPQL SQL과 유사하게  JPA에서 사용하는 쿼리 언어
    //@Query를 이용하는 경우
    //1. 조인과 같이 복잡한 쿼리를 실행하려고 할 떄
    //2. 원하는 속성만 추출해서 Object[] 로 처리하거나 DTO 로 처리가능
    //3. 속성 값 중 nativeQuery 속성 값을 true로 지정하면 SQL구문으로 사용이 가능함.
    @Query("select b FROM Board b where b.title like concat('%',:keyword,'%')")
    Page<Board> findKeyword(String keyword, Pageable pageable);

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

}
