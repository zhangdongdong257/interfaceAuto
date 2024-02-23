/*
 * @Author: 霍格沃兹测试开发学社
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */

package com.wework.filter.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wework.entity.TokenEntity;
import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class TokenCacheFilter implements Filter {
    TokenCache tokenCache = new TokenCacheFileImpl();
    final String SECRET_PARAM_NAME = "corpsecret";

    public final Logger logger = getLogger(lookup().lookupClass());

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
        // 从缓存中获取token
        ObjectMapper mapper = new ObjectMapper();
        String secret = requestSpec.getRequestParams().get(SECRET_PARAM_NAME);
        TokenEntity tokenEntity = tokenCache.load(secret);
        logger.debug("------加载token--------"+ tokenEntity);
        // 如果缓存存在则直接返回token
        if (tokenEntity != null) {
            try {
                return new ResponseBuilder()
                        .setStatusCode(200)
                        .setContentType("application/json")
                        .setBody(mapper.writeValueAsString(tokenEntity))
                        .build();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        Response response = filterContext.next(requestSpec, responseSpec);
        TokenEntity te = response.body().as(TokenEntity.class);
        logger.debug("------返回token--------"+ te.getAccessToken());
        // 写入缓存
        tokenCache.dump(secret,te,te.getExpiresIn());
        return response;
    }


}
