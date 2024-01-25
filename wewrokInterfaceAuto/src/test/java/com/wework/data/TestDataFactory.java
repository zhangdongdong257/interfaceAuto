package com.wework.data;



import com.wework.data.mapper.EntityFileMapping;
import com.wework.entity.DepartmentEntity;
import com.wework.utils.TestDataUtil;

import java.io.IOException;
import java.util.List;

public class TestDataFactory {
    //用于返回测试数据的静态方法的工程类，可以被@MethodSource进行调用

    /**
     * 返回部门列表对象
     *
     * @throws IOException
     * */

    public static List<DepartmentEntity> getDepartmentTestData() throws IOException{
        return TestDataUtil.loadYamlData(EntityFileMapping.DEPARTMENT_ENTITY);
    }
}
