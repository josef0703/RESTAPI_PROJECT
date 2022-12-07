package com.example.gradletest3.dao.movie.image;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MovieImageDTO {
    private int mv_num;
    private int mv_size;
    private String org_file_name;
    private Date mv_regdat;
    private String mv_del_yn;
    private String mv_extension;
}
