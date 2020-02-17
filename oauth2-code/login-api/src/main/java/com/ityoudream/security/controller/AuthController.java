package com.ityoudream.security.controller;

import com.ityoudream.security.model.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/me")
    public TokenInfo me(HttpSession session) {
        TokenInfo info = (TokenInfo) session.getAttribute("token");
        log.info("me:"  +  session.getId());
        return info;
    }
    @GetMapping("/oauth/callback")
    public void callback(@RequestParam String code, String state, HttpServletResponse res, HttpSession session) {
        log.info("state is:" + state);
        String oauthServiceUrl = "http://gateway.ityoudream.com:9070/token/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("auth","123456");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://127.0.0.1:8080/auth/oauth/callback");

        log.info("params:" + params);
        HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity<MultiValueMap<String,String>>(params,headers);
        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
        session.setAttribute("token", response.getBody().init());
        log.info("sessionId1:" + session.getId());
        try {
            res.sendRedirect("http://127.0.0.1:8081/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
        log.info("logout..");
    }

}
