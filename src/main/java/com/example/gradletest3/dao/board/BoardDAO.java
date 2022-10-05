package com.example.gradletest3.dao.board;

import com.example.gradletest3.dao.user.UserDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardDAO {

    @Select("select * from board_table where b_del_yn='N'")
    public List<BoardDTO> boardList();

    @Insert("insert into board_table (b_num,b_writer,b_title,b_content,b_regdat) values (board_table_seq.nextval,#{b_writer},#{b_title},#{b_content},sysdate)")
    public int boardwrite(BoardDTO boardDTO);

    @Select("select * from board_table where b_del_yn='N' and b_num=#{b_num}")
    public BoardDTO boardone(int b_num);

    @Update("update board_table set b_title=#{b_title}, b_content=#{b_content},b_moddat=to_char(sysdate,'YY-MM-DD') where b_num=#{b_num}")
    public int boardupdate(BoardDTO boardDTO);

    @Update("update board_table set b_del_yn='Y' where b_num=#{b_num}")
    public int boarddelete(int b_num);
}
