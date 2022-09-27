package com.example.gradletest3.dao.board;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardDAO {

    @Select("select * from board_table where b_del_yn='N'")
    public List<BoardDTO> boardList();
}
