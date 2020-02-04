package com.ityoudream.security.controller;

import com.ityoudream.security.order.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
@Slf4j
public class OrderController {

//    private RestTemplate restTemplate = new RestTemplate();
    public OrderInfo create(@RequestBody OrderInfo order, @RequestHeader String username ) {
        log.info("user is " + username);
//        PriceInfo price = restTemplate.getForObject("http://localhost:9060/prices/" + order.getProductId(), PriceInfo.class);
//        log.debug("price is " + price.getPrice());
        return order;
    }

    @GetMapping("/{id}")
    public OrderInfo getInfo(@PathVariable Integer id, @RequestHeader String username) {
        log.info("user is " + username);
        log.info("orderId is " +  id);
        OrderInfo  info = new OrderInfo();
        info.setId(id);
        info.setProductId(id * 5);
        return info;
    }
}
