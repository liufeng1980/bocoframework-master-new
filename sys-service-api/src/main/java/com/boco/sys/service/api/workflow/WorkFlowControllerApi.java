package com.boco.sys.service.api.workflow;

import com.boco.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Api(value = "工作流模块", description = "工作流模块相关接口")
public interface WorkFlowControllerApi {
    @ApiOperation("启动流程")
    ResponseResult startWorkFlow();

    @ApiOperation("1.呼叫中心提交订单")
    @PostMapping(value = "/submitBill")
    ResponseResult submitBill();

    @ApiOperation("2.运营科审核")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "businessKey",value = "呼叫流程主键id",required = true,paramType = "path",dataType = "String")
            }
    )
    ResponseResult YunYingKeAudit(@PathVariable( "businessKey") String businessKey);

    @ApiOperation("3.高路公司审核")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "businessKey",value = "呼叫流程主键id",required = true,paramType = "path",dataType = "String")
            }
    )
    ResponseResult gaoLuAudit(@PathVariable( "businessKey") String businessKey);


    @ApiOperation("4.分公司审核")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "businessKey",value = "呼叫流程主键id",required = true,paramType = "path",dataType = "String")
            }
    )
    ResponseResult fenZhongXinAudit(@PathVariable( "businessKey") String businessKey);

    @ApiOperation(" 5.高路公司的反馈")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "businessKey",value = "呼叫流程主键id",required = true,paramType = "path",dataType = "String")
            }
    )
    ResponseResult gaoluFeedbackAudit(@PathVariable( "businessKey") String businessKey);
}
