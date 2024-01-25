package com.wework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wework.entity.ConfigEntity;
import com.wework.entity.EnvEntity;

import java.io.IOException;

public class ConfigUtil {


    /**
     * 返回环境配置
     */
    public static EnvEntity getEnv(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        EnvEntity envEntity = null;
        try {
            envEntity = mapper.readValue(EnvEntity.class.getClassLoader().getResourceAsStream("data/env.yaml"), EnvEntity.class);
            return envEntity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 返回环境信息
     */
    public static ConfigEntity getConfig(String env){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ConfigEntity configEntity = null;
        try {
            configEntity = mapper.readValue(ConfigEntity.class.getClassLoader().getResourceAsStream("data/"+env+"-config.yaml"), ConfigEntity.class);
            return configEntity;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
