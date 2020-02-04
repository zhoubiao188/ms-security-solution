package com.ityoudream.security.controller;

import com.ityoudream.security.model.Auth;
import com.ityoudream.security.model.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private RestTemplate restTemplate = new RestTemplate();
    @PostMapping("/login")
    public void login(@RequestBody Auth auth, HttpServletRequest request, HttpSession session) {
        String oauthServiceUrl = "http://gateway.ityoudream.com:9070/token/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("auth","123456");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();
        params.add("username", auth.getUsername());
        params.add("password", auth.getPassword());
        params.add("grant_type", "password");
        params.add("scope", "read write");

        log.info("params:" + params);
        HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity<MultiValueMap<String,String>>(params,headers);
        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
        session.setAttribute("token", response.getBody());
        log.info("sessionId1:" + session.getId());
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
        log.info("logout..");
    }

}
