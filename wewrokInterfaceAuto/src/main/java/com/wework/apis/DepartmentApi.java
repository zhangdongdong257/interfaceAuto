package com.wework.apis;

import com.jayway.jsonpath.JsonPath;
import com.wework.entity.DepartmentEntity;
import com.wework.utils.RestAssuerdUtil;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.restassured.RestAssured.given;

public class DepartmentApi extends WeWork{
    public String secret;

    private RestAssuerdUtil given =  new RestAssuerdUtil();

    public DepartmentApi(){
        super();
        secret = config.getContactsSecret();
        token = getToken(secret);
    }

    public Response createDepartment(DepartmentEntity dce) {
        return given()
                .queryParams("access_token", token)
                .body(dce)
                .contentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8)) // 设置请求的内容类型为 JSON，并指定字符编码为 UTF-8
                .when()
                .post("/department/create")
                .then()
                .extract().response();
    }

    public void delete(Integer depart_id) {
        given()
                .queryParams("access_token", token)
                .param("id", depart_id)
                .when()
                .post("/department/delete")
                .then()
                .extract().response();
    }

    public List<Integer> getDepartmentIds() {
        String depsText = given()
                .queryParams("access_token", token)
                .when()
                .post("/department/simplelist")
                .then()
                .extract().response().getBody().asString();
        return JsonPath.read(depsText, "$..id");
    }

    public static class CustomFilter implements Filter {

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            // 在请求前执行的逻辑
            System.out.println("Before Request");

            // 执行请求
            Response response = ctx.next(requestSpec, responseSpec);

            // 在收到响应后执行的逻辑
            System.out.println("After Response");

            // 返回处理后的响应
            return response;
        }
    }




}
