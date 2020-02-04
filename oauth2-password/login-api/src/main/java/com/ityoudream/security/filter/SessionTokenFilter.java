package com.ityoudream.security.filter;

import com.ityoudream.security.model.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class SessionTokenFilter extends ZuulFilter {
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
            requestContext.addZuulRequestHeader("Authorization", "bearer "+token.getAccess_token());
        }
        return null;
    }
}
