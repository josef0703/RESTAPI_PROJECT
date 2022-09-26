package com.example.gradletest3.controller;

import com.example.gradletest3.dao.UserDTO;
import com.example.gradletest3.dao.Userdao;
import com.example.gradletest3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public String login(UserDTO userDTO) {
        return null;
    }

}
