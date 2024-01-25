package com.wework.apitest;


import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Slf4j
public class TokenTest {


    //corpId: "wwd636e0fdbb8f87fa"
    //contactsSecret: "IHoWEnuwhAoC94I0WB897rVFyYDkyOLmqIzf0Q3MhI0"
    @Test
    void getToken() {
        String corpid = "wwd636e0fdbb8f87fa";
        String secret = "IHoWEnuwhAoC94I0WB897rVFyYDkyOLmqIzf0Q3MhI0";
        Response response = given()
                //获取请求的日志 参数信息 请求头 请求参数
                .log().all()
                .param("corpid", corpid)
                .param("corpsecret", secret)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                //获取响应日志 响应body 响应头信息
                .log().all()
                .extract().response();
        log.info(response.path("access_token").toString());
    }
}
