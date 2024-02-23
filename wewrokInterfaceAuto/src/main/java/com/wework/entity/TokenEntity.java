/*
 * @Author: 霍格沃兹测试开发学社
 * @Desc: '更多测试开发技术探讨，请访问：https://ceshiren.com/t/topic/15860'
 */

package com.wework.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenEntity {
       private Integer errcode;
       private String errmsg;
       @JsonProperty("access_token")
       private String accessToken;
       @JsonProperty("expires_in")
       private Integer expiresIn;
}
