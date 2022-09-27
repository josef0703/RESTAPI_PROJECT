package com.example.gradletest3.service.user;

import com.example.gradletest3.dao.user.UserDTO;
import com.example.gradletest3.dao.user.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userdao;

    public UserService(UserDAO userdao) {
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
