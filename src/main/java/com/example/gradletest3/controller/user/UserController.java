package com.example.gradletest3.controller.user;

import com.example.gradletest3.dao.user.UserDTO;
import com.example.gradletest3.service.user.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }


    //회원가입 페이지

    @GetMapping("/join")
    public String joinPage() {
        return "/user/join";
    }

    //회원가입 POST
    @PostMapping("/join")
    public String join(UserDTO userDTO) {

        int result = userService.join(userDTO);

        System.out.println(result);

        return "redirect:/user/login";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    //로그인 POST
    @PostMapping("/login")
    public String login(UserDTO userDTO, Model model, HttpServletRequest req) {

        String returnURL = "";

        UserDTO result = userService.login(userDTO);


        if (result.getUserid() != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", result.getUserid());
            System.out.println("로그인 성공");
            returnURL = "redirect:/board/boardlist";
        } else {
            System.out.println("로그인 실패");
            returnURL = "/user/login";
        }


        System.out.println(result.getUserid());
        model.addAttribute("userInfo", result.getUserid());


        return returnURL;
    }
}
