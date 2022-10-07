package com.example.gradletest3.controller.board;

import com.example.gradletest3.dao.board.BoardDTO;
import com.example.gradletest3.service.board.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//        if (user == null) {
//            returnURL = "/user/login";
//        } else {
            model.addAttribute("boardlist", boardService.boardList());
            model.addAttribute("user", user);
            returnURL = "/board/boardlist";
//        }


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

    @GetMapping("/boardone")
    public String boardone(@RequestParam("b_num") int b_num ,Model model, HttpServletRequest req, HttpServletResponse res) {

        BoardDTO result = boardService.boardone(b_num);
//        System.out.println(result.getB_num());

        model.addAttribute("findbynum", boardService.boardone(b_num));
//        System.out.println(req.getParameter("num"));

        //조회수 로직
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("cookie.getname" + cookie.getName());
                System.out.println("cookie.getvalue" + cookie.getValue());

                System.out.println("parameter 값 : "+req.getParameter("b_num"));

                //세션 value값에 b_num값이 있으면 조회수 안올라감.
                //세션을 없애고 쿠키로 로그인 확인해야하는지 고민 필요
                if (!cookie.getValue().contains(req.getParameter("b_num"))) {
                    cookie.setValue(cookie.getValue() + "_" + req.getParameter("b_num"));
                    cookie.setMaxAge(60 * 60 * 2);
                    res.addCookie(cookie);
                    boardService.boardViewCount(b_num);
                    System.out.println("변경 후 쿠키값 : "+cookie.getValue());

                }
            }
        } else {
            Cookie newCookie = new Cookie("visit_cookie", req.getParameter("b_num"));
            newCookie.setMaxAge(60 * 60 * 2);
            res.addCookie(newCookie);
            boardService.boardViewCount(b_num);
        }


        return "/board/boardone";
    }

    @GetMapping("/boardupdate/{b_num}")
    public String boardUpdate(@PathVariable("b_num") int b_num, Model model) {
        model.addAttribute("findbynum", boardService.boardone(b_num));
        return "/board/boardupdate";
    }

    @PostMapping("/boardupdate/{b_num}")
    public String boardUpdateOk(BoardDTO boardDTO, Model model) {

        String returnURL = "";
//        System.out.println(boardDTO.getB_title());
//        System.out.println(boardDTO.getB_content());
        int result = boardService.boardupdate(boardDTO);

        if (result == 1) {
            returnURL = "redirect:/board/boardone/" + boardDTO.getB_num();
        } else {
            returnURL = "redirect:/board/boardupdate/" + boardDTO.getB_num();
        }
        return returnURL;
    }

    @GetMapping("/boarddelete/{b_num}")
    public String boarddelete(@PathVariable("b_num") int b_num, Model model) {
        model.addAttribute("findbynum", boardService.boardone(b_num));
        return "/board/boarddelete";
    }

    @PostMapping("/boarddelete/{b_num}")
    public String boarddeleteOk(@PathVariable("b_num") int b_num, Model model) {
        String returnURL = "";
        int result = boardService.boarddelete(b_num);
        System.out.println("result 값 : " + result);

        if (result == 1) {
            returnURL = "redirect:/board/boardlist";
        } else {
            returnURL = "redirect:/board/boardone/" + b_num;
        }
        return returnURL;
    }


}
