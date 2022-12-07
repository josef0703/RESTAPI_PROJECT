package com.example.gradletest3.dao.fileupload;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface FileUploadDAO {

    @Insert("insert into file_table (f_num,b_num,f_size,org_file_name,stored_file_name,f_extension)\n" +
            "values (file_table_seq.nextval,#{b_num},#{f_size},#{org_file_name},#{stored_file_name},#{f_extension})")
//    @Select("select * from file_table")
    public int fileUpload(FileUploadDTO fileUploadDTO);

    @Select("select * from file_table where b_num=#{b_num} and f_del_yn='n'")
    public List<FileUploadDTO> getFileList(int b_num);

    //다운로드 할 파일 찾기
    @Select("select * from file_table where f_num=#{f_num}")
    public FileUploadDTO getdownloadFile(int f_num);

    @Update("update file_table set f_del_yn='y' where f_num=#{f_num}")
    public int fileDelete(int f_num);

}
