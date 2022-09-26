package com.example.gradletest3.service;

import com.example.gradletest3.dao.UserDTO;
import com.example.gradletest3.dao.Userdao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Userdao userdao;

    public UserService(Userdao userdao) {
        this.userdao = userdao;
    }

    public List<UserDTO> selectAll() {
        return userdao.selectAll();
    }

    public int join(UserDTO userDTO) {
        return userdao.join(userDTO);
    }

    public UserDTO login(UserDTO userDTO) {
        return userdao.login(userDTO);
    }
}
