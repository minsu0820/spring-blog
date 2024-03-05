package com.cos.blog.config.auth;

import com.cos.blog.model.Users;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class PrincipalDetail implements UserDetails {
    private Users user;

    public PrincipalDetail(Users principal) {
        this.user= principal;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
    //계정의 권한 목록을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collectors = new ArrayList<>();
//        collectors.add(() -> {
//            return "ROLE_" + user.getRole();
//        });
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }



    public void setUser(Users user) {
        this.user = user;
    }
}
