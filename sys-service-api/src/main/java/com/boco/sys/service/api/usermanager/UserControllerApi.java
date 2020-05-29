package com.boco.sys.service.api.usermanager;

import com.boco.framework.model.ucenter.ext.JkptBaseUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户接口", description = "用户接口页面管理接口，提供页面的增、删、改、查")
public interface UserControllerApi {
    @ApiOperation("根据用户名查询用户信息")
    JkptBaseUserExt getUserByName(String userName);
}
