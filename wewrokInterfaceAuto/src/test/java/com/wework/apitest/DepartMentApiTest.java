package com.wework.apitest;

import com.wework.apis.DepartmentApi;
import com.wework.entity.DepartmentEntity;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;

public class DepartMentApiTest {

    private static DepartmentApi departmentApi;

    @BeforeAll
    public static void setup(){
        departmentApi = new DepartmentApi();
    }

    @Test
    public void createDepartmentTest(){
        // 1. 准备已存在部门
        DepartmentEntity createDepartmentDepartment = new DepartmentEntity("技术部", "JISHU1", 1, 1, 2);
        departmentApi.createDepartment(createDepartmentDepartment);
        // 2. 有存在部门的情况下，创建相同信息的部门失败
        Response response = departmentApi.createDepartment(createDepartmentDepartment);
        assertThat(response.path("errcode"), is(60008));
        // 3. 删除部门
        departmentApi.delete(createDepartmentDepartment.getId());
        // 4. 查询部门删除成功
        List<Integer> ids = departmentApi.getDepartmentIds();
        assertThat(ids, not(hasItem(createDepartmentDepartment.getId())));
        // 5. 再次创建部门
        departmentApi.createDepartment(createDepartmentDepartment);
        // 6. 查询部门创建成功
        ids = departmentApi.getDepartmentIds();
        assertThat(ids, hasItem(createDepartmentDepartment.getId()));

    }

    @ParameterizedTest
    @MethodSource("com.wework.data.TestDataFactory#getDepartmentTestData")
    @DisplayName("添加部门")
    @Description("添加部门并删除部门")
    public void createDepartmentTest(DepartmentEntity departmentEntity){
        departmentApi.createDepartment(departmentEntity);
        // 2. 有存在部门的情况下，创建相同信息的部门失败
        Response response = departmentApi.createDepartment(departmentEntity);
        assertThat(response.path("errcode"), is(60008));
        // 3. 删除部门
        departmentApi.delete(departmentEntity.getId());
        // 4. 查询部门删除成功
        List<Integer> ids = departmentApi.getDepartmentIds();
        assertThat(ids, not(hasItem(departmentEntity.getId())));
        // 5. 再次创建部门
        departmentApi.createDepartment(departmentEntity);
        // 6. 查询部门创建成功
        ids = departmentApi.getDepartmentIds();
        assertThat(ids, hasItem(departmentEntity.getId()));

    }


}
