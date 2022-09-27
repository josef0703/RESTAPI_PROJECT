package com.example.gradletest3.controller.user;

import com.example.gradletest3.dao.user.UserDTO;
import com.example.gradletest3.service.board.BoardService;
import com.example.gradletest3.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }



    //회원가입 페이지
    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    //회원가입 POST
    @PostMapping("/join")
    public String join(UserDTO userDTO) {

        int result = userService.join(userDTO);

        System.out.println(result);

        return "redirect:/login";
    }

    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //로그인 POST
    @PostMapping("/login")
    public String login(UserDTO userDTO, Model model) {

        String returnURL = "";
        UserDTO result = userService.login(userDTO);

        if (result.getUserid() != null) {
            returnURL = "redirect:/boardList";
        } else {

            returnURL = "redirect:/login";
        }


        System.out.println(result.getUserid());
        model.addAttribute("userInfo", result.getUserid());


        return returnURL;
    }



}
