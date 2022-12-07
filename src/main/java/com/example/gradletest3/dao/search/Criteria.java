package com.example.gradletest3.dao.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Criteria {

    //현재 페이지
    private int pageNum;

    //한 페이지 당 보여질 게시물 갯수
    private int amount;

    //스킵 할 게시물 수 ( (pageNum-1)*amount)
    private int skip;

    public Criteria() {
        this(1, 10);
        this.skip = 0;
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.skip = (pageNum - 1) * amount;

    }


    @Override
    public String toString() {
        return "Criteria [pageNum=" + pageNum + ", amount=" + amount + "]'";
    }
}
