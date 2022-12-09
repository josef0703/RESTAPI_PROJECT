package com.example.gradletest3.service.user;

import com.example.gradletest3.dao.user.KakaoDTO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KakaoService {

    public String getKakaoAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        String grant_type = "authorization_code";
        String client_id = "6c751e5e4ce3f689acb1ae4884eec4d0";
        String redirect_uri = "http://localhost:8080/user/kakao";
        String line = "";
        String result = "";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + grant_type);
            sb.append("&client_id=" + client_id);  // REST_API_KEY 입력
            sb.append("&redirect_uri=" + redirect_uri); //redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();


            //결과 코드가 200이면 성공
            int responseCode = conn.getResponseCode();
            log.info("responseCode =" + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON 파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("access_token : " + access_Token);
            log.info("refresh_token : " + refresh_Token);

            br.close();
            bw.close();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return access_Token;
    }

    public void createKakaoUser(String token) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String line = "";
        String result = "";
        KakaoDTO kakaoDTO = null;

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성 , acces_token 전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("create responseCode : " + responseCode);

            //요청을 통해 얻은 JSON 타입의 response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("create response body : " + result);


            //Gson 라이브러리로 JSON 파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("haS_email").getAsBoolean();
            String email = "";
            if (hasEmail) {
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            log.info("id : " + id);
            log.info("email : " + email);
            log.info("nickname : " + nickname);
            kakaoDTO.setKakaoAccount(kakaoDTO.getKakaoAccount());
            kakaoDTO.setId((long) id);
            kakaoDTO.setProperties(kakaoDTO.getProperties());

            br.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        return kakaoDTO;
    }

    public Map<String, Object> getUserInfo(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String line = "";
        String result = "";
        KakaoDTO kakaoDTO = null;

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성 , acces_token 전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("create responseCode : " + responseCode);

            //요청을 통해 얻은 JSON 타입의 response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("create response body : " + result);


            //Gson 라이브러리로 JSON 파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
//            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("haS_email").getAsBoolean();
//            String email = "";
//            if (hasEmail) {
//                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
//            }
//            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            log.info("id : " + id);
//            log.info("email : " + email);
//            log.info("nickname : " + nickname);
//            resultMap.put("nickname", nickname);
//            resultMap.put("email", email);
            resultMap.put("id", id);

            br.close();


        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return resultMap;
    }

}


