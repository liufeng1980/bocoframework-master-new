package com.boco.sys.service.api.ucenter;

import com.boco.framework.model.ucenter.ext.JkptBaseUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {
    @ApiOperation("根据用户账号查询用户信息")
    public JkptBaseUserExt getUserext(String username);
}
