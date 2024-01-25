package com.wework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigEntity {
    private String baseUrl;
    private String corpId;
    private String contactsSecret;

}
