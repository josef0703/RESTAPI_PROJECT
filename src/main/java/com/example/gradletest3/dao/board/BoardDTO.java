package com.example.gradletest3.dao.board;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BoardDTO {

    //게시판 번호
    private int b_num;
    //작성자
    private String b_writer;
    //제목
    private String b_title;
    //내용
    private String b_content;
    //조회수
    private int b_hit;
    //삭제여부
    private String b_del_yn;
    //등록일자
    private Date b_regdat;
    //수정일자
    private Date b_moddat;

}
