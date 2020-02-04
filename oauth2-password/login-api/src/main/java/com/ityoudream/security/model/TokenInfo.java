package com.ityoudream.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenInfo implements Serializable {
   private String access_token;
   private String token_type;
   private String expires_in;
   private String scope;
}
