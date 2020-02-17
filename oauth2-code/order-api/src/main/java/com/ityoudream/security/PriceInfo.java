package com.ityoudream.security;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceInfo {
    private Integer id;
    private BigDecimal price;
}
