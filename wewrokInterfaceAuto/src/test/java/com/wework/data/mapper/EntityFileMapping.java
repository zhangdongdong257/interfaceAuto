package com.wework.data.mapper;


import com.wework.entity.DepartmentEntity;

public enum EntityFileMapping {
    // 保存实体类与文件的对应关系

    DEPARTMENT_ENTITY(DepartmentEntity.class, "department-data.yaml");

    private  Class<?> entityClass;
    private  String filePath;

    EntityFileMapping(Class<?> entityClass, String filePath){
        this.entityClass = entityClass;
        this.filePath = filePath;
    }

    public Class<?> getEntityClass(){
        return entityClass;
    }

    public String getFilePath(){
        return filePath;
    }
}
