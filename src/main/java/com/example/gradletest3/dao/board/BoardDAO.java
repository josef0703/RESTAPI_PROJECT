package com.example.gradletest3.dao.board;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardDAO {

    @Select("select * from board_table where b_del_yn='N'")
    public List<BoardDTO> boardList();

    @Insert("insert into board_table (b_num,b_writer,b_title,b_content,b_regdat) values (board_table_seq.nextval,#{b_writer},#{b_title},#{b_content},sysdate)")
    public int boardwrite(BoardDTO boardDTO);
}
