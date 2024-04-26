package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;
    //insert
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i-> {
            Board board = Board.builder()
                    .title("title : " + i)
                    .content("content  : " + i)
                    .writer("user : " +(i%10)) //0~9번까지
                    .build();
            Board result = boardRepository.save(board);
            log.info("BNO : " + result.getBno());

        });
    }
    //select
    @Test
    public void testSelect() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();
        log.info(board);
    }
    //update
    //entity 는 생성시 불변이 좋으나 변경이 일어날 경우 최소한으로 변경되게 설계
    @Test
    public void testUpdate() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();

        board.change("update title1000", "update content 100");
        boardRepository.save(board);
    }

    //delete
    @Test
    public void testDelete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);

    }

    //Pageable 과 Page<E> 타입을 이용한 페이징 처리
    //페이징 처리는 Pageable 타입의 객체를 구성하여 파라미터로 전달

    //Pageable은 인터페이스로 설계되어 있고, 일반적으로 PageRequest.of()를 이용해서 개발함.
    //PageRequest.of(페이지번호, 사이즈) : 페이지번호는 0번부터
    //PageRequest.of(페이지번호, 사이즈, Sort) : sort객체를 통한 정렬조건 추가
    //PageRequest.of(페이지번호, 사이즈, Sort.Direction, 속성) : 정렬 방향과 여러 속성 추가 지정
    //Pageable로 값을 넘기면 반환타입은 Page<T>를 이용하게 됨

    @Test
    public void  testPaging() {
        Pageable pageable = PageRequest.of
                (0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total page : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " + result.getSize());
        log.info("다음페이지 여부 : " + result.hasNext());
        log.info("이전페이지 여부 : " + result.hasPrevious());


        List<Board> boardList = result.getContent();
        boardList.forEach(board -> log.info(board));
    }
    //쿼리 메소드 및 @Query 테스트
    @Test
    public void testQueryMethod() {
        Pageable pageable = PageRequest.of(0,10);
        String keyword = "title";
        Page<Board> result =  boardRepository.findByTitleContainingOrderByBnoDesc(
            keyword,
            pageable
        );
        result.getContent().forEach(board -> log.info(board));
    }
    @Test
    public void testQueryAnnotation() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Board> result = boardRepository.findKeyword("title",pageable);
        result.getContent().forEach(board -> log.info(board));
    }
    @Test
    public void testGetTime() {
        log.info(boardRepository.getTime());
    }
    @Test
    public void testSearch1() {
        //2page order by bno desc
        Pageable pageable = PageRequest.of
                (1,10,Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }
}
