package com.example.gradletest3.dao.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoDTO {

    private Long id;
    private Properties properties;
    private KakaoAccount kakaoAccount;


    @Getter
    public static class KakaoAccount{
        private String email;
    }

    public static class Properties {
        private String nickname;
    }
}
