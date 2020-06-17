package com.boco.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SysOauth2Util {

    public UserJwt getUserJwtFromHeader(HttpServletRequest request){
        Map<String, Object> jwtClaims = Oauth2Util.getJwtClaimsFromHeader(request);
        if(jwtClaims == null || StringUtils.isEmpty(jwtClaims.get("loginid").toString())){
            return null;
        }
        UserJwt userJwt = new UserJwt();
        userJwt.setIsadmin(Integer.parseInt(jwtClaims.get("isadmin")+""));
        userJwt.setUsername(jwtClaims.get("username").toString());
        userJwt.setRoleid(Integer.parseInt(jwtClaims.get("roleid").toString()));
        userJwt.setStatus(Integer.parseInt(jwtClaims.get("status").toString()));
        userJwt.setLoginid(jwtClaims.get("loginid").toString());
        userJwt.setUserkeyid(Integer.parseInt(jwtClaims.get("userkeyid").toString()));
        userJwt.setOrgName(jwtClaims.get("orgName").toString());
        userJwt.setOrgType(Integer.parseInt(jwtClaims.get("orgType").toString()));
        userJwt.setOrgid(jwtClaims.get("orgid").toString());
        userJwt.setUserId(jwtClaims.get("userid").toString());

        return userJwt;
    }

    @Data
    public class UserJwt{
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

        /**
         * userId
         */
        private String userId;
    }
}
