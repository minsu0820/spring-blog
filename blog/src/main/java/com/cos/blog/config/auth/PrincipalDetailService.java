package com.cos.blog.config.auth;


import com.cos.blog.Repository.UserRepository;
import com.cos.blog.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users Principal = userRepository.findByUsername(username).orElseThrow(() ->
        {
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다:" + username);
        });
        return new PrincipalDetail(Principal);
    }

}

