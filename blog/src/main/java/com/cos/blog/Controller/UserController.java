package com.cos.blog.Controller;

import com.cos.blog.Service.UserService;
import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


//인증이 안된 사용자들이 출입할수있는경로 /auth/gjdyd
//

@Controller
@RequiredArgsConstructor
public class UserController {


    final private UserService userService;

    private final PrincipalDetailService principalDetailService;


    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/imform")
    public String userInform(Model model) {
        return "user/userForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoLogin(String code,HttpSession session) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();

        /**
         * http header 생성
         */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        /**\
         *          header 와 body를 하나의 오브젝트에 담기
         */
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "8a1501bb24aca5550eb474bc5e3dbffd");
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap> kakaoTokenRequest = new HttpEntity<>(params, headers);

        /**
         *      http 요청을 post 방식으로, stinrg의 response 받기
         */
        ResponseEntity<String> respons = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
//        OAuthToken OAuthToken = null;
        OAuthToken oAuthToken = objectMapper.readValue(respons.getBody(), OAuthToken.class);

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> ProfileResponse = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );
        ObjectMapper objectMapper2 = new ObjectMapper();
//        KakaoProfile kakaoProfile = null;
        KakaoProfile kakaoProfile = objectMapper2.readValue(ProfileResponse.getBody(), KakaoProfile.class);

        UUID tempPwd = UUID.randomUUID();
        Users kakaoUser = Users.builder()
                .username(kakaoProfile.getProperties().getNickname() + kakaoProfile.getId())
                .password(tempPwd.toString())
                .email("test1").build();

        // 카카로 정보를 이용해서 userDeatail로 만들고, 그 정보로 직접 Security context에 넣어서 로그인된 세션을 만들어주기 위함
        UserDetails userDetails = principalDetailService.loadUserByUsername(kakaoUser.getUsername());
        userService.kakaoLogin(kakaoUser, (PrincipalDetail) userDetails, session);

        return "redirect:/";
    }

}

