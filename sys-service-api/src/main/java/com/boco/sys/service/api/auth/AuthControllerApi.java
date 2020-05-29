package com.boco.sys.service.api.auth;


import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.ucenter.request.LoginRequest;
import com.boco.framework.model.ucenter.response.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户认证",description = "用户认证接口")
public interface AuthControllerApi {
    @ApiOperation("登录")
    public ResponseResult<String> login(LoginRequest loginRequest);

    @ApiOperation("退出")
    public ResponseResult logout();

    @ApiOperation("查询用户jwt令牌")
    public JwtResult userjwt();
}
