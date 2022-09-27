package com.example.gradletest3.controller.board;

import com.example.gradletest3.service.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    // 게시판 리스트
    @GetMapping("/boardlist")
    public String boardList(Model model) {
        System.out.println("들어옴");
        model.addAttribute("boardlist", boardService.boardList());

        return "boardlist";
    }

    // 게시판 작성페이지


    //게시판 작성

}
