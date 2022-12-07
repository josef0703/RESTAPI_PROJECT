package com.example.gradletest3.dao.movie.paylist;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.annotation.MatchesPattern;

@Repository
@Mapper
public interface MoviePayListDAO {
    @Insert("insert into mvpay_table (mp_num,mp_price)\n" +
            "values (mp_num_seq.nextval,#{mp_price})")
    public int addPayList(MoviePayListDTO moviePayListDTO);
}
