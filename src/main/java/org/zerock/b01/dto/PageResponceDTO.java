package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponceDTO<E> {
    private int page;
    private int size;
    private int total;

    //시작페이지 번호
    private int start;
    //끝페이지 번호
    private int end;

    // 이전 페이지의 존재 여부
    private boolean prev;

    // 다음 페이지 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponceDTO (PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        if(total <=0) {
            return;
        }
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        //화면에 표시할 페이지번호 갯수
        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;
        // 화면의 시작 번호
        this.start = this.end - 9;
        // 데이터 갯수로 계산한 마지막 페이지번호
        int last = (int)(Math.ceil((total/(double)size)));
        this.end = end > last ? last : end;

        this.prev = this.start > 1;
        // next
        this.next = total > this.end * this.size;

    }

}
