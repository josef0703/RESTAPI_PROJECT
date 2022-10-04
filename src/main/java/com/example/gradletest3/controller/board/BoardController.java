package com.example.gradletest3.controller.board;

import com.example.gradletest3.dao.board.BoardDTO;
import com.example.gradletest3.service.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판 리스트
    @GetMapping("/boardlist")
    public String boardList(Model model, HttpSession session) {
        String returnURL = "";
        System.out.println("들어옴");
        Object user = session.getAttribute("user");
        System.out.println("user =" + user);
        if (user == null) {
            returnURL = "/user/login";
        } else {
            model.addAttribute("boardlist", boardService.boardList());
            model.addAttribute("user", user);
            returnURL = "/board/boardlist";
        }


        return returnURL;
    }

    // 게시판 작성페이지
    @GetMapping("/boardwrite")
    public String boardwrite(HttpSession session, Model model) {
        String returnURL = "";
        Object user = session.getAttribute("user");
        if (user == null) {
            returnURL = "/user/login";
        } else {
            model.addAttribute("user", user);
            returnURL = "/board/boardwrite";
        }


        return returnURL;
    }


    //게시판 작성
    @PostMapping("/boardwrite")
    public String boardwrite(BoardDTO boardDTO) {
        int result = boardService.boardwrite(boardDTO);
        String returnURL = "";
        System.out.println(result);

        if (result == 1) {
            System.out.println("성공");
            returnURL = "redirect:/board/boardlist";
        } else {
            System.out.println("실패");
            returnURL = "redirect:/board/boardwrite";
        }

        return returnURL;
    }

    @GetMapping("/boardone/{b_num}")
    public String boardone(@PathVariable("b_num") int b_num, Model model) {

        BoardDTO result = boardService.boardone(b_num);
        System.out.println(result.getB_num());

//        model.addAttribute("findbynum", boardService.boardone());
        return "/board/boardone";
    }

    @GetMapping("/boardupdate/")
    public String boardUpdate() {
        return null;
    }

}
