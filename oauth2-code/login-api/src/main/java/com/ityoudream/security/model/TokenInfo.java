package com.ityoudream.security.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TokenInfo implements Serializable {
   private String access_token;
   private String refresh_token;
   private String token_type;
   private long expires_in;
   private String scope;
   private LocalDateTime expireTime;

   public TokenInfo init () {
      expireTime  = LocalDateTime.now().plusSeconds(expires_in - 3);
      return this;
   }

   public boolean isExpired() {
      return expireTime.isBefore(LocalDateTime.now());
   }
}
