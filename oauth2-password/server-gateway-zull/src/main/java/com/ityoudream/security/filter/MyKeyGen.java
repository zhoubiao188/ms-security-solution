package com.ityoudream.security.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyKeyGen extends DefaultRateLimitKeyGenerator {

    public MyKeyGen(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        super(properties, rateLimitUtils);
    }

    @Override
    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
        return super.key(request, route, policy);
    }
}
