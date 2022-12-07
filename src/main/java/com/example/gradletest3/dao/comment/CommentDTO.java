package com.example.gradletest3.dao.comment;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private int b_num;
    private int r_num;
    private String r_writer;
    private String r_content;
    private Date r_regdat;
    private String r_del_yn;

}
