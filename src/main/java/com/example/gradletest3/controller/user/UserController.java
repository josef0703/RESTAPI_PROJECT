package com.example.gradletest3.controller.user;

import com.example.gradletest3.dao.user.EmailAuthRequestDto;
import com.example.gradletest3.dao.user.UserDTO;
import com.example.gradletest3.service.email.EmailService;
import com.example.gradletest3.service.user.KakaoService;
import com.example.gradletest3.service.user.NaverService;
import com.example.gradletest3.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;

@Controller
@RequestMapping("user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final KakaoService kakaoService;
    private final NaverService naverService;


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
            , @RequestParam("userpasswd") String userpasswd) {

        String returnURL = "";
        String passwd = userDTO.getUserpasswd();
        log.info(passwd);

        UserDTO result = userService.login(userDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(passwd, result.getUserpasswd())) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", result.getUserid());
            System.out.println("로그인 성공");
            returnURL = "redirect:/board/boardlist";
        } else {
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

    @GetMapping("/kakao1")
    public String login_kakao() {
//        log.info("token : " + authrizationCode);
//        log.info("token : " + code);

        return "/user/social_login";
    }

    @GetMapping("/kakao")
    @ResponseBody
    public String getToken(@RequestParam("code") String code) {
        log.info("token :" + code);

        String accessToken = kakaoService.getKakaoAccessToken(code);
        Map<String,Object> result = kakaoService.getUserInfo(accessToken);

        log.info(String.valueOf(result.get("email")));
        log.info(String.valueOf(result.get("id")));
        log.info(String.valueOf(result.get("nickname")));

        return "redirect:/";
    }

    @GetMapping("/naver1")
    public String login_naver(Model model) throws UnsupportedEncodingException {
        String redirectURI = URLEncoder.encode("http://localhost:8081/user/naver", "UTF-8");
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        model.addAttribute("redirectURI", redirectURI);
        model.addAttribute("state", state);

        log.info("redirecturi = " + redirectURI);
        log.info("state = " + state);

        return "/user/social_login";

    }

    @GetMapping("/naver")
    @ResponseBody
    public String getNaverToken(@RequestParam String code) {
        log.info("code : " + code);
        String naverAccessToken = naverService.getNaverAccessToken(code);
        log.info("naverAccessToken : " + naverAccessToken);
        Map<String, Object> result = naverService.getNaverInfo(naverAccessToken);
        log.info("id : " + result.get("id"));
        log.info("email : " + result.get("email"));
        return "redirect:/";
    }
}
