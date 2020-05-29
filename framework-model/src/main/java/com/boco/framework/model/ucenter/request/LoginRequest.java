package com.boco.framework.model.ucenter.request;

import com.boco.framework.model.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value = "用户登陆信息")
public class LoginRequest extends RequestData {

    @ApiModelProperty("用户名")
    String username;
    @ApiModelProperty("密码")
    String password;
    @ApiModelProperty("非必要参数")
    String verifycode;

}
