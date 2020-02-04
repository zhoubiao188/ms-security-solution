package com.ityoudream.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditLogFilter extends ZuulFilter {
    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 2;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        log.info("audit log insert");
        return null;
    }
}
