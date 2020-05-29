package com.boco.framework.model.user;


import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private Integer status;
}
