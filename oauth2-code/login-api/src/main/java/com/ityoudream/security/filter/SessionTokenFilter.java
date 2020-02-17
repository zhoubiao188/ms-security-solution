package com.ityoudream.security.filter;

import com.ityoudream.security.model.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class SessionTokenFilter extends ZuulFilter {
    private RestTemplate restTemplate = new RestTemplate();
    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpSession session = request.getSession();
        TokenInfo token = (TokenInfo)session.getAttribute("token");
        log.info("sessionId2:" + session.getId());
        log.info("token:" + token);
        if (token != null) {
            String tokenValue = token.getAccess_token();
            if (token.isExpired()) {
                //TODO 刷新令牌
                String oauthServiceUrl = "http://gateway.ityoudream.com:9070/token/oauth/token";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("auth","123456");

                MultiValueMap<String,String> params = new LinkedMultiValueMap<String, String>();
                params.add("grant_type", "refresh_token");
                params.add("refresh_token", token.getRefresh_token());

                log.info("params:" + params);
                HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity<MultiValueMap<String,String>>(params,headers);

                try {
                    ResponseEntity<TokenInfo> newToken = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
                    session.setAttribute("token", newToken.getBody().init());
                    tokenValue = newToken.getBody().getAccess_token();
                }catch (Exception e) {
                    requestContext.setSendZuulResponse(false);
                    requestContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
                    requestContext.getResponse().setContentType("application/json");
                }
            }
            requestContext.addZuulRequestHeader("Authorization", "bearer "+tokenValue);
        }
        return null;
    }
}
