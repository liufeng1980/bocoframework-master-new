package com.boco.sys.service.ucenter.auth.service;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
@ToString
public class UserJwt extends User {

//    private String id;
//    private String name;
//    private String userpic;
//    private String utype;
//    private String companyId;



    /**
     * 用户姓名
     */
    private String username;

    /**
     * 角色名称：关联角色表(JKPT_BASE_ROLE)
     */
    private Integer roleid;


    /**
     * 1 可用  0 停用
     */
    private Integer status;

    /**
     * 登录编号
     */
    private String loginid;

    /**
     * 1=超级管理员 0=不是
     */
    private Integer isadmin;

    /**
     * 主键
     */
    private Integer userkeyid;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 机构类型
     */
    private Integer orgType;

    /**
     * 所属机构：关联机构表(JKPT_BASE_ORG)
     */
    private String orgid;

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
