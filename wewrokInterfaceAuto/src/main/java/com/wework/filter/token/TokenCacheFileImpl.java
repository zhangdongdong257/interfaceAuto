/*
 * @Author: 霍格沃兹测试开发学社
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */

package com.wework.filter.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wework.entity.TokenEntity;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class TokenCacheFileImpl implements TokenCache {

    final String PREFIX_SUFFIX = "src/test/resources/data/";
    final String FILE_SUFFIX = "-token.yaml";
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    public final Logger logger = getLogger(lookup().lookupClass());

    @Override
    public void dump(String secret, TokenEntity tokenEntity, Integer expires) {
        try {
            // 处理缓存有效期，记录过期的时间
            tokenEntity.setExpiresIn((int) (System.currentTimeMillis() / 1000) + expires);
            logger.debug(PREFIX_SUFFIX + secret + FILE_SUFFIX+"写入文件路径");
            mapper.writeValue(new File(PREFIX_SUFFIX + secret + FILE_SUFFIX), tokenEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TokenEntity load(String secret) {
        try {
            TokenEntity tokenEntity = mapper.readValue(new File(PREFIX_SUFFIX + secret + FILE_SUFFIX), TokenEntity.class);
            int time = tokenEntity.getExpiresIn() - (int) (System.currentTimeMillis() / 1000);
            // 已过期则不返回
            if (time < 0) {
                return null;
            }
            tokenEntity.setExpiresIn(time);
            return tokenEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
