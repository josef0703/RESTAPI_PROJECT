package com.example.gradletest3.dao.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserDAO {
    



    @Select("select * from user_info where userrole='user'")
    public List<UserDTO> selectAll();

    @Insert("insert into user_info (usernum,name,userid,userpasswd,useremail,user_regdat) values(user_table_seq.nextval,#{name},#{userid},#{userpasswd},#{useremail},sysdate)")
    public int join(UserDTO userDTO);


    @Select("select userid,userpasswd from user_info where userid=#{userid}")
//    public UserDTO login(UserDTO userDTO);
    public UserDTO login(UserDTO userDTO);

    //카카오 회원가입
//    @Insert()



}