package com.wework.filter.token;

import com.wework.entity.TokenEntity;

public interface TokenCache {
    void dump(String secret, TokenEntity tokenEntity, Integer expires);

    TokenEntity load(String secret);
}
