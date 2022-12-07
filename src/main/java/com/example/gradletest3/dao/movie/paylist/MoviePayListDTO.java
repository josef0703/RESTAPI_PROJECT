package com.example.gradletest3.dao.movie.paylist;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class MoviePayListDTO {

    // 구매UID
    private int mp_num;
    // 영화 가격
    private String mp_price;
    // 영화 카테고리
    private String mp_category;
    // 영화 좌석
    private String mp_seatnum;
    // 영화이름
    private String mp_name;
    // 계정 fk
    private String usernum;
    // 구매자 일므
    private String user_name;


}
