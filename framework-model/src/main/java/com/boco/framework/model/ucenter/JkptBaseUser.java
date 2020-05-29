package com.boco.framework.model.ucenter;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JkptBaseUser implements Serializable {
    /**
     * 用户编号-公司编号
     */
    private String usercode;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 性别：1-男，2-女，0-空
     */
    private String sex;

    /**
     * 民族
     */
    private String nation;

    /**
     * 手机号码
     */
    private String contact;

    /**
     * 所属机构：关联机构表(JKPT_BASE_ORG)
     */
    private String orgid;

    /**
     * 角色名称：关联角色表(JKPT_BASE_ROLE)
     */
    private Integer roleid;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户唯一标示  无实际意义 规则序列号+@ITC
     */
    private String userid;

    /**
     * 1 可用  0 停用
     */
    private Integer status;

    /**
     * 登录编号
     */
    private String loginid;

    /**
     * 职务id
     */
    private Integer dutiesid;

    /**
     * 1=超级管理员 0=不是
     */
    private Integer isadmin;

    /**
     * 创建人
     */
    private String creationuserid;

    /**
     * 创建时间
     */
    private Date creationtime;

    /**
     * 最后最该人
     */
    private String modifyuserid;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 人员排序
     */
    private Integer ordering;

    /**
     * 对应捷思税平台用户Id
     */
    private String videouserid;

    /**
     * 主键
     */
    private Integer userkeyid;

    /**
     * 用户头像路径
     */
    private String usericonpath;


    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 机构类型
     */
    private Integer orgType;


}
