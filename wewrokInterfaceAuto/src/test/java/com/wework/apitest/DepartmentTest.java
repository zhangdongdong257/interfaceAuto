package com.wework.apitest;

import com.jayway.jsonpath.JsonPath;
import com.wework.entity.DepartmentEntity;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class DepartmentTest {

    String getToken() {
        String corpid = "wwd636e0fdbb8f87fa";
        String secret = "IHoWEnuwhAoC94I0WB897rVFyYDkyOLmqIzf0Q3MhI0";
        Response response = given()
                //获取请求的日志 参数信息 请求头 请求参数
                .param("corpid", corpid)
                .param("corpsecret", secret)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                //获取响应日志 响应body 响应头信息
                .extract().response();
        return response.path("access_token").toString();
    }

    @Test
    void createDepartment() {
        String reqJson = "{\n" +
                "\"name\":\"" + "技术部4" + "\",\n" +
                "\"name_en\":\"" + "JISHU4" + "\",\n" +
                "\"parentid\":" + 1 + ",\n" +
                "\"order\":" + 1 + ",\n" +
                "\"id\":" + 5 + "\n" +
                "}";

        Response response = given()
                .body(reqJson)
                .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8)) // 设置请求的内容类型为 JSON，并指定字符编码为 UTF-8
                .log().all()
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + getToken())
                .then()
                .log().all()
                .statusCode(200) //断言状态码
                .body("errcode", is(0)).extract().response();
    }

    @ParameterizedTest
    @CsvSource({
            "技术部1,jishu1,1,1,2",
            "技术部2,jishu2,1,1,3",
            "技术部3,jishu3,1,1,4",
    })
    void createDepartmentParams(String name, String name_en, Integer parentid, Integer order, Integer id) {
        String reqJson = "{\n" +
                "\"name\":\"" + name + "\",\n" +
                "\"name_en\":\"" + name_en + "\",\n" +
                "\"parentid\":" + parentid + ",\n" +
                "\"order\":" + order + ",\n" +
                "\"id\":" + id + "\n" +
                "}";
        Response response = given()
//                .body(new DepartmentCreateEntity("技术部","JISHU1",1,1,2))
                .body(reqJson)
                .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8)) // 设置请求的内容类型为 JSON，并指定字符编码为 UTF-8
                .log().all()
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + getToken())
                .then()
                .log().all()
                .statusCode(200) //断言状态码
                .body("errcode", is(0))
                .extract().response();
    }

    @Test
    void createDepartmentExist(){
        // 1. 准备已存在部门
        DepartmentEntity createDepartment = new DepartmentEntity("技术部","JISHU1",1,1,2);
        given()
                .body(createDepartment)
                .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8)) // 设置请求的内容类型为 JSON，并指定字符编码为 UTF-8
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + getToken())
                .then()
                .statusCode(200) //断言状态码
                .body("errcode", is(0));
        // 2. 有存在部门的情况下，创建相同信息的部门失败
        given()
                .body(createDepartment)
                .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8)) // 设置请求的内容类型为 JSON，并指定字符编码为 UTF-8
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + getToken())
                .then()
                .statusCode(200) //断言状态码
                .body("errcode", is(60008));
        // 3. 删除部门
        given()
                .param("id",createDepartment.getId())
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=" + getToken())
                .then()
                .statusCode(200) //断言状态码
                .body("errcode", is(0));
        // 4. 查询部门删除成功
        String depsText = given()
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/simplelist?access_token=" + getToken())
                .then()
                .statusCode(200) //断言状态码
                .body("errcode", is(0))
                .extract().response().getBody().asString();
        List<Integer> ids = JsonPath.read(depsText, "$..id");
        MatcherAssert.assertThat(ids,not(hasItem(createDepartment.getId())));
        // 5. 再次创建部门
        given()
                .body(createDepartment)
                .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8)) // 设置请求的内容类型为 JSON，并指定字符编码为 UTF-8
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + getToken())
                .then()
                .statusCode(200) //断言状态码
                .body("errcode", is(0));
        // 6. 查询部门创建成功
        depsText = given()
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/simplelist?access_token=" + getToken())
                .then()
                .statusCode(200) //断言状态码
                .body("errcode", is(0))
                .extract().response().getBody().asString();
        ids = JsonPath.read(depsText, "$..id");
        MatcherAssert.assertThat(ids,hasItem(createDepartment.getId()));



    }



}
