package com.cos.blog.Service;


import com.cos.blog.Repository.UserRepository;
import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.model.Role;
import com.cos.blog.model.Users;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    final private UserRepository userRepository;

    final private BCryptPasswordEncoder encoder;

    final private PrincipalDetailService principalDetailService;

//    final private AuthenticationManager authenticationManager;

    @Transactional
    public void 회원가입(Users user) {

        String rawPwd = user.getPassword();
        String encPwd = encoder.encode(rawPwd);
        user.setPassword(encPwd);
        user.setRole(Role.USER);

        userRepository.save(user);

    }

    @Transactional
    public void update(Users user,PrincipalDetail principal) {

        Users updateUser = userRepository.findById(user.getId()).get();

        updateUser.setEmail(user.getEmail());
        if (!Objects.equals(user.getPassword(), updateUser.getPassword())) {
            updateUser.setPassword(encoder.encode(user.getPassword()));
        }
        principal.setUser(updateUser);
//
    }

    /**
     *  카카오 로그인을 통해 정보를 가지고 오고.
     *
     *
     */

    @Transactional
    public void kakaoLogin(Users user, PrincipalDetail principalDetail,HttpSession session) {
        // 이미 회원가입 되어있으면 로그인만, 안돼있으면 회원가입까지.
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            userRepository.save(user);
        }
        //
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities());

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
    }

//    public Users 로그인(Users user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }

}
