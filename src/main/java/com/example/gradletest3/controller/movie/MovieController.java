package com.example.gradletest3.controller.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@RestController
public class MovieController {

    @GetMapping("/movielist")
    public String callAPI(Model model) {

        String jsonInString = "";

        HashMap<String, Object> result = new HashMap<String, Object>();

        try {
            RestTemplate restTemplate = new RestTemplate();


            String targetDate = "targetDt=20220927";

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            String URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=7342b5d609eda545be26e3eafab5988a";
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(URL + "&" + targetDate).build();

            //이 한줄의 코드로 API 호출해 MAP 타입으로 전달 받음
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());

            LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("boxOfficeResult");
            ArrayList<Map> boxoffList = (ArrayList<Map>) lm.get("dailyBoxOfficeList");
            HashMap<String, Object> mnlist = new HashMap<>();
//
            for (Map obj : boxoffList) {
////                mnlist.put(obj.get("rnum"), obj.get("movieNm"));
                mnlist.put("moviename", obj.get("movieNm"));
//                mnlist.put("rank", obj.get("rank"));6
//                mnlist.put("rankInten", obj.get("rankInten"));
//                mnlist.put("openDt", obj.get("openDt"));
//                mnlist.put("audiCnt", obj.get("audiCnt"));
//
            }
//
//            System.out.println("사이즈 : "+boxoffList.size());

            //데이터를 제대로 전달 받았는지 확인 String 형태로 파싱해줌
            ObjectMapper mapper = new ObjectMapper();




            model.addAttribute("movie", jsonInString);
//            model.addAttribute("moviejson", mnlist);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            result.put("statuisCode", e.getRawStatusCode());
            result.put("body", e.getStatusText());
            System.out.println("dfdfd");
            System.out.println(e.toString());

        } catch (Exception e) {
            result.put("statisCpde", "999");
            result.put("body", "exception 오류");
            System.out.println(e.toString());

        }

        return jsonInString;
    }

}
