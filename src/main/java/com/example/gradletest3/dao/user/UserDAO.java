package com.example.gradletest3.dao.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserDAO {



    @Select("select * from user_table where userrole='user'")
    public List<UserDTO> selectAll();

    @Insert("insert into user_table (usernum,name,userid,userpasswd,useremail) values(user_table_seq.nextval,#{name},#{userid},#{userpasswd},#{useremail})")
    public int join(UserDTO userDTO);


    @Select("select userid,userpasswd from user_table where userid=#{userid}")
    public UserDTO login(UserDTO userDTO);



}
