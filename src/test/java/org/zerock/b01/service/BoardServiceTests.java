package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;

import java.util.NoSuchElementException;


@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());
        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample test")
                .content("Sample content")
                .writer("user1")
                .build();
        long bno = boardService.register(boardDTO);
        log.info("bno = " + bno);
    }
    @Test
    public void testReadOne() {
        long bno = 101L;
        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
    }
//    @Test
//    public void testModify() {
//        long bno = 101L;
//        BoardDTO boardDTO = boardService.readOne(bno);
//
//        boardDTO.setTitle("Updated Title");
//        boardDTO.setContent("Updated Content");
//
//        boardService.modify(boardDTO);
//
//        BoardDTO updatedBoard = boardService.readOne(bno);
//
//        log.info(updatedBoard);
//    }
    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("Updated Title1")
                .content("Updated Content1")
                .build();
        boardService.modify(boardDTO);
        log.info(boardService.readOne(boardDTO.getBno()));
    }
    @Test
    public void testRemove() {
        long bno = 101L;

        // 1. 삭제 전 게시글 조회
        BoardDTO boardDTO = boardService.readOne(bno);
        log.info("Before removal: " + boardDTO);
        // 2. 게시글 삭제
        boardService.remove(bno);
        // 3. 삭제 후 게시글 조회 시도
        Assertions.assertThrows(Exception.class, () -> {
            boardService.readOne(bno);
        });
        log.info("After removal: Board with bno " + bno + " is removed.");
    }

}
