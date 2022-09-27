package com.example.gradletest3.dao.board;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BoardDTO {

    private int b_num;
    private String b_writer;
    private String b_title;
    private String b_content;
    private int b_hit;
    private String b_del_yn;
    private Date b_regdat;
    private Date b_moddat;

}
