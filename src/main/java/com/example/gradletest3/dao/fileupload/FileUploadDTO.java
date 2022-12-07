package com.example.gradletest3.dao.fileupload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FileUploadDTO {
    private int f_num;
    private int b_num;
    private int f_size;
    private String org_file_name;
    private String stored_file_name;
    private Date f_regdat;
    private String f_del_yn;
    private String f_extension;
}
