package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    final private PrincipalDetailService principalDetailService;

    @Bean
    BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
//        return auth.build();
//    }


    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(encodePWD());
        authenticationProvider.setUserDetailsService(principalDetailService);
        return authenticationProvider;
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/", "/auth/**", "/WEB-INF/**", "/js/**", "/css/**", "/image/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(form ->
                        form.loginPage("/auth/loginForm")
                                .loginProcessingUrl("/auth/loginProc")
                                .defaultSuccessUrl("/"));
//                        .permitAll())
//                .logout(logout -> logout.permitAll());
        return http.build();
    }
}
