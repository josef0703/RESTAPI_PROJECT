package com.example.gradletest3.dao.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageMakerDTO {
    // 시작 페이지
    private int startPage;
    // 끝 페이지
    private int endpage;
    // 이전 페이지, 다음페이지 존재 유무
    private boolean prev, next;
    // 전체 게시물 수
    private int total = 100;
    // 현재 페이지, 페이지당 게시물 표시수 정보
    private Criteria cri;

    public PageMakerDTO(int total, Criteria cri) {
        this.total = total;
        this.cri = cri;
        //마지막 페이지
        this.endpage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
        //시작 페이지
        this.startPage = this.endpage - 9;
        //전체 마지막 페이지
        int realEnd = (int) (Math.ceil(total * 1.0 / cri.getAmount()));

        System.out.println("readend : " + realEnd);

        //전체 마지막 페이지가 화면에 보이는 마지막페이지보다 작은 경우 보이는 페이지 값 조정
        if (realEnd < this.endpage) {
            this.endpage = realEnd;
        }

        //시작 페이지 값이 1보다 큰 경우 true
        this.prev = this.startPage > 1;
        //마지막 페이지 값이 1보다 큰 경우 true
        this.next = this.endpage < realEnd;

    }
}
