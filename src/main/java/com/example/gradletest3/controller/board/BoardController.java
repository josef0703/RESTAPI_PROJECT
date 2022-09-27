package com.example.gradletest3.controller.board;

import com.example.gradletest3.dao.board.BoardDTO;
import com.example.gradletest3.service.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판 리스트
    @GetMapping("/boardlist")
    public String boardList(Model model, HttpSession session) {
        System.out.println("들어옴");
        Object user = session.getAttribute("user");
        model.addAttribute("boardlist", boardService.boardList());
        model.addAttribute("user", user);

        return "/board/boardlist";
    }

    // 게시판 작성페이지
    @GetMapping("/boardwrite")
    public String boardwrite(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);

        return "/board/boardwrite";
    }


    //게시판 작성
    @PostMapping("/boardwrite")
    public String boardwrite(BoardDTO boardDTO) {
        int result = boardService.boardwrite(boardDTO);
        String returnURL = "";
        System.out.println(result);

        if (result == 1) {
            System.out.println("성공");
            returnURL = "/board/boardlist";
        } else {
            System.out.println("실패");
            returnURL = "/board/boardwrite";
        }

        return returnURL;
    }
}
