package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

import java.util.List;

/*
    BoardSearch를 상속받는 구현체
 */
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        //Q도메인을 이용한 쿼리 작성 및 테스트
        //Querydsl의 목적은 "타입" 기반으로 "코드"를 이용해서 JPQL 쿼리를 생성 후 실행
        //Q도메인은 이 때 코드를 만드는 대신에 클래스가 Q도메인 클래스

        //1. Q도메인 생성
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board); // select ~ from board
        //booleanBuilder() 사용
        BooleanBuilder booleanBuilder = new BooleanBuilder();// (
        booleanBuilder.or(board.title.contains("11"));      //title like
        booleanBuilder.or(board.content.contains("11"));    //content like

//        query.where(board.title.contains("1")); //where title like
        query.where(booleanBuilder);                        // )
        query.where(board.bno.gt(0L));                 // bno > 0
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> title = query.fetch(); //JPQL 쿼리 실행
        long count = query.fetchCount();   //쿼리실행
        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        //1 Qdomain 객체 생성
        QBoard board = QBoard.board;
        //2 ql 작성
        JPQLQuery<Board> query = from(board); //select ~ from board
        if ((types != null && types.length >0) && keyword!= null) {
            //검색 조건과 키워드가 있는 경우
            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (
            for (String type: types) {
                switch (type) {
                    case "t" :
                        booleanBuilder.or(board.title.contains(keyword)); //title like concat('%', keyword, '%')
                        break;
                    case "c" :
                        booleanBuilder.or(board.content.contains(keyword)); //content like concat('%', keyword, '%')
                        break;
                    case "w" :
                        booleanBuilder.or(board.writer.contains(keyword)); //writer like concat('%', keyword, '%')
                        break;
                }
            } //for end
            query.where(booleanBuilder);                         // )
        } //if end
        //bno>0
        query.where(board.bno.gt(0L));
        //paging
        this.getQuerydsl().applyPagination(pageable,query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();

        //Page<T> 형식으로 반환 : Page<Board>
        //PageImpl을 통해서 반환 : (알아온 데이터, pageable, (총개수)total)
        return new PageImpl<>(list,pageable,count);
    }
}
