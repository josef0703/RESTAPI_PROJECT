package com.example.gradletest3.service.user;

import com.example.gradletest3.dao.user.UserDTO;
import com.example.gradletest3.dao.user.UserDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserDAO userdao;

    public UserService(UserDAO userdao) {
        this.userdao = userdao;
    }

    public List<UserDTO> selectAll() {
        return userdao.selectAll();
    }

    public int join(UserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setUserpasswd(encoder.encode(userDTO.getUserpasswd()));

        return userdao.join(userDTO);
    }

    public UserDTO login(UserDTO userDTO) {
        return userdao.login(userDTO);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
