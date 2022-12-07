package com.example.gradletest3.dao.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements UserDetails {

    private int usernum;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요")
    private String userpasswd;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userid;

    @Email(message = "이메일 형식으로 입력해주세요.")
    private String useremail;

    private String userrole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();

        if (this.userrole.equals("admin")) {
            auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            auth.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return auth;
    }

    @Override
    public String getPassword() {
        return userpasswd;
    }

    @Override
    public String getUsername() {
        return userid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    @Builder
//    public UserDTO(int usernum, String name, String userpasswd, String userid, String useremail) {
//        this.usernum = usernum;
//        this.name = name;
//        this.userpasswd = userpasswd;
//        this.userid = userid;
//        this.useremail = useremail;
//    }
}
