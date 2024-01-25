package com.wework.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentEntity {
    private String name;
    private String name_en;
    private int parentid;
    private int order;
    private int id;
}
