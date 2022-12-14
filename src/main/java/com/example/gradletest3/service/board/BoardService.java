package com.example.gradletest3.service.board;

import com.example.gradletest3.dao.board.BoardDAO;
import com.example.gradletest3.dao.board.BoardDTO;
import com.example.gradletest3.dao.search.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardDAO boardDAO;

    public BoardService(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public List<BoardDTO> boardList(Criteria cri) {
        return boardDAO.boardList(cri);

    }

    public int getTotal() {
        return boardDAO.getTotal();
    }

    public int boardwrite(BoardDTO boardDTO) {
        return boardDAO.boardwrite(boardDTO);
    }

    public BoardDTO boardone(int b_num) {
        return boardDAO.boardone(b_num);
    }

    public int boardupdate(BoardDTO boardDTO) {
        return boardDAO.boardupdate(boardDTO);
    }

    public int boarddelete(int b_num) {
        return boardDAO.boarddelete(b_num);
    }

    public int boardViewCount(int b_num) {
        return boardDAO.boardViewCount(b_num);
    }
}
