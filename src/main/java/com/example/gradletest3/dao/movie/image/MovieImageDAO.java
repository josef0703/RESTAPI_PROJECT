package com.example.gradletest3.dao.movie.image;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MovieImageDAO {

    @Insert("insert into mvImg_table (mv_num,mv_size,org_file_name,mv_extension)\n" +
            "values(mvImg_table_seq.nextval,#{mv_size},#{org_file_name},#{mv_extension})")
    public int addImage(MovieImageDTO movieImageDTO);
}
