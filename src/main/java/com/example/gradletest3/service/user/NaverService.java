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
public class NaverService {
    public String getNaverAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String grant_type = "authorization_code";
        String redirect_uri = "http://localhost:8081/user/naver";
        String client_id = "EgmSzlLPq2t3fwcT0yRm";
        String client_secret = "FM0eQL3HhF";
        String reqURL = "https://nid.naver.com/oauth2.0/token";
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
            sb.append("&client_secret=" + client_secret);
//            sb.append("&redirect_uri=" + redirect_uri); //redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.info("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body : " + result);

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

    public Map<String, Object> getNaverInfo(String token) {
        Map<String, Object> resultMap = new HashMap<>();
        String reqURL = "https://openapi.naver.com/v1/nid/me";
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

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
            String email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();

            log.info("id : " + id);
            log.info("email :" + email);
            resultMap.put("id", id);
            resultMap.put("email", email);

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
