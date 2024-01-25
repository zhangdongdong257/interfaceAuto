package com.wework.apis;

import com.wework.entity.ConfigEntity;
import com.wework.entity.EnvEntity;
import com.wework.utils.ConfigUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;

import static io.restassured.RestAssured.given;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class WeWork {
    public String  corpid;

    public EnvEntity env;
    public ConfigEntity config;

    public  String token;

    public final Logger logger = getLogger(lookup().lookupClass());


    public WeWork(){
        env = ConfigUtil.getEnv();
        config = ConfigUtil.getConfig(env.getCurrentEnv());
        corpid = config.getCorpId();
        RestAssured.baseURI = config.getBaseUrl();
    }

    public String getToken(String sercret) {
        logger.debug("------这是token值----------"+token);
        if(token==null||token.equals("")) {
            Response response = given()
                    //获取请求的日志 参数信息 请求头 请求参数
                    .param("corpid", corpid)
                    .param("corpsecret", sercret)
                    .when()
                    .get("/gettoken")
                    .then()
                    .log()
                    .all()
                    //获取响应日志 响应body 响应头信息
                    .extract().response();
            token = response.path("access_token").toString();
        }

        return token;
    }



}
