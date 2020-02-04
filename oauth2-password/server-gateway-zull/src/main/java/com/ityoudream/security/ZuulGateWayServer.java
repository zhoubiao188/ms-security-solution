package com.ityoudream.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulGateWayServer {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGateWayServer.class,args);
    }
}
