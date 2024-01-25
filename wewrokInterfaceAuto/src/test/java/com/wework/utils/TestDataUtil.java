package com.wework.utils;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wework.data.mapper.EntityFileMapping;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestDataUtil {
    //测试文件路径
    static  final String TEST_DATA_FILE_PATH = "src/test/resources/data/";

    static  final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    /**
     * 用于yaml文件解析
     *
     * @param entityFileMapping 实体类与文件的对应关系
     * @param <T>
     * @return 返回实体类列表
     * @throws IOException
     * */
    public static <T> List<T> loadYamlData(EntityFileMapping entityFileMapping) throws IOException {
        //获取yaml文件
        File file = new File(TEST_DATA_FILE_PATH + entityFileMapping.getFilePath());
        //获取type实例用来转换Java对象类型
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        //指定
        JavaType javaType = typeFactory.constructParametricType(List.class,entityFileMapping.getEntityClass());
        return objectMapper.readValue(file, javaType);
    }

    /**
     * 写入实体类列表到对应yaml文件中
     *
     * @param entityFileMapping
     * @param <T>
     * @return 返回void
     * @throws IOException
     * */
    public static <T> void dumpYmalData(EntityFileMapping entityFileMapping, List<T> testDataList) throws IOException{
        if(testDataList.size() == 0){
            throw new IllegalArgumentException("数组长度不应该为0");
        }

        //判断类型是否一致
        if(!testDataList.get(0).getClass().equals(entityFileMapping.getEntityClass())){
            throw new ClassCastException("列表类型和文件类型不匹配");
        }
        File file = new File(TEST_DATA_FILE_PATH + entityFileMapping.getFilePath());
        objectMapper.writeValue(file, testDataList);

    }


}
