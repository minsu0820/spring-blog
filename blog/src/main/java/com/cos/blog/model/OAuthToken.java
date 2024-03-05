package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
//@RequiredArgsConstructor

public class OAuthToken {

    private String token_type;
    private String access_token;
    private String refresh_token;
    private String scope;

    private int expires_in;

    private int refresh_token_expires_in;


}
