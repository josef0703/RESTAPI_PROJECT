package com.example.gradletest3.dao.comment;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentDAO {

    @Insert("insert into reply_table (r_num,b_num,r_writer,r_content)\n" +
            "values(reply_table_seq.nextval,#{b_num},#{r_writer},#{r_content})")
    public int commentInsert(CommentDTO commentDTO);

    @Select("select r_num,b_num,r_writer,r_content,\n" +
            "to_char(r_regdat,'YYYY-MM-DD HH24:MI:SS') as r_regdat\n" +
            "from reply_table where b_num=#{b_num}")
    public List<CommentDTO> getList(int b_num);

    @Select("select count(*) from reply_table where b_num=#{b_num}")
    public int getCount(int b_num);

}
