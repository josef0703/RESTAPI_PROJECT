package com.example.gradletest3.controller.user;

import com.example.gradletest3.dao.user.EmailAuthRequestDto;
import com.example.gradletest3.dao.user.UserDTO;
import com.example.gradletest3.service.email.EmailService;
import com.example.gradletest3.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    //회원가입 페이지

    @GetMapping("/join")
    public String joinPage() {
        return "/user/join";
    }

    //회원가입 POST
    @PostMapping("/join")
    public String join(@Valid UserDTO userDTO) {
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
    public String login(UserDTO userDTO, Model model, HttpServletRequest req
            , @RequestParam("userpasswd") String userpasswd)
            {

        String returnURL = "";
                String passwd = userDTO.getUserpasswd();
        log.info(passwd);

        UserDTO result = userService.login(userDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(passwd,result.getUserpasswd())){
            HttpSession session = req.getSession(true);
            session.setAttribute("user", result.getUserid());
            System.out.println("로그인 성공");
            returnURL = "redirect:/board/boardlist";
        }else {
            System.out.println("로그인 실패");
            returnURL = "/user/login";
        }

        model.addAttribute("userInfo", result.getUserid());




        return returnURL;
    }

    @PostMapping("/mainConfirm")
    @ResponseBody
    public String mailConfirm(@RequestBody EmailAuthRequestDto emailAuthRequestDto, Model model) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendEmail(emailAuthRequestDto.getEmail());
        model.addAttribute("code", authCode);
        return authCode;
    }




}
