package com.cos.blog.Controller.api;


import com.cos.blog.dto.ResponseDto;
import com.cos.blog.Service.UserService;
import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserApiController {



    final private UserService userService;



    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody Users user) {
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/user/update")
    public ResponseDto<Integer> update(@RequestBody Users user, @AuthenticationPrincipal PrincipalDetail principal) {

        userService.update(user,principal);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}

