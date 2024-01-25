package com.wework.utils;

import com.wework.entity.DepartmentEntity;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

public class RestAssuerdUtil {

    public Response post(String token, String path, Object obj) {
        return given()
                .queryParams("access_token", token)
                .body(obj)
                .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8)) // 设置请求的内容类型为 JSON，并指定字符编码为 UTF-8
                .when()
                .post(path)
                .then()
                .extract().response();


    }

    public Response post(String token, String path, String id) {
        return given()
                .queryParams("access_token", token)
                .param("id", id)
                .when()
                .post(path)
                .then()
                .extract().response();


    }




}
