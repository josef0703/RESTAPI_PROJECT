package com.example.gradletest3.service.board;

import com.example.gradletest3.dao.board.BoardDAO;
import com.example.gradletest3.dao.board.BoardDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardDAO boardDAO;

    public BoardService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public List<BoardDTO> boardList() {
        return boardDAO.boardList();

    }
}