package com.example.gradletest3.dao.board;

import com.example.gradletest3.dao.search.Criteria;
import com.example.gradletest3.dao.user.UserDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardDAO {

//    @Select("select * from board_table2 where b_del_yn='N'")
//    @Select("select b_num,b_title,b_content,b_writer,b_regdat from \n" +
//            " (select rownum as rn, b_num,b_title,b_content,b_writer,b_regdat\n" +
//            " from board_table2 where rownum <= #{pageNum}*#{amount})\n" +
//            " where rn > (#{pageNum}-1) * #{amount})")
    @Select("select b_num,b_title,b_content,b_writer,b_regdat,b_hit from (select rownum as rn, b_num,b_title,b_content,b_writer,b_regdat,b_hit from board_table2 where rownum <= #{pageNum}*#{amount}) where rn > (#{pageNum}-1) * #{amount}")
    public List<BoardDTO> boardList(Criteria cri);

    @Select("select count(*) from board_table2")
    public int getTotal();


    //    @Insert("insert into board_table2 (b_num,b_writer,b_title,b_content,b_regdat) values (board_table2_seq.nextval,#{b_writer},#{b_title},#{b_content},sysdate)")
    @Insert("insert into board_table2 (b_num,b_writer,b_title,b_content) values (board_table_seq.nextval,#{b_writer},#{b_title},#{b_content})")
    public int boardwrite(BoardDTO boardDTO);


    @Select("select * from board_table2 where b_del_yn='N' and b_num=#{b_num}")
    public BoardDTO boardone(int b_num);

    @Update("update board_table2 set b_title=#{b_title}, b_content=#{b_content},b_moddat=to_char(sysdate,'YY-MM-DD') where b_num=#{b_num}")
    public int boardupdate(BoardDTO boardDTO);

    @Update("update board_table2 set b_del_yn='Y' where b_num=#{b_num}")
    public int boarddelete(int b_num);

    @Update("update board_table2 set b_hit=b_hit+1 where b_num=#{b_num}")
    public int boardViewCount(int b_num);

    //댓글 가져오기



}
