package com.example.gradletest3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class OpenApiController {

    @GetMapping("/dust")
    public String dustApi() {
        StringBuffer result = new StringBuffer();
        try {
            String urlstr = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc?" +
                    "serviceKey=7RC%2B6XxyPY4ms4oni8dxfA2z0J2LmPEdwVh3UX%2BX6drp4fwlnV07V5K%2BKhPGQ%2F7b3fqPX1Ki68L%2BrbCxEnj07w%3D%3D" +
                    "&returnType=xml" +
                    "&pageNo=1" +
                    "&numOfRows=100" +
                    "&searchDate=2020-11-14";

            URL url = new URL(urlstr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String returnline;
            result.append("<xmp>");
            while ((returnline = br.readLine()) != null) {
                result.append(returnline + "\n");
            }
            urlConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result + "</xmp>";
    }
}
