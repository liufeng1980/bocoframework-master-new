package com.boco.sys.service.workflow.controller;

import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.web.BaseController;
import com.boco.sys.service.api.workflow.WorkFlowControllerApi;


import com.boco.sys.service.workflow.service.CallFlowService;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flow")
public class WorkFlowController extends BaseController implements WorkFlowControllerApi {

    @Autowired
    CallFlowService callFlowService;
    @Autowired
    TaskRuntime taskRuntime;

    @PreAuthorize("hasAuthority('startWorkFlow')")
    //@RequestMapping("/startworkflow")
    @GetMapping("/startworkflow")
    @Override
    public ResponseResult startWorkFlow() {
        //SysOauth2Util sysOauth2Util = new SysOauth2Util();
        //SysOauth2Util.UserJwt userJwt = sysOauth2Util.getUserJwtFromHeader(request);
        return ResponseResult.SUCCESS();
    }

//    @RequestMapping("/start")
//    public String start(){
//        return "aaa";
//    }
//
//
//    @RequestMapping(value = "/deploy", produces = "text/html; charset=utf-8")
//    public String deploy(){
//        //callFlowService.deploy();
//        return "流程部署";
//    }



    /**
     * 呼叫中心提交订单
     * @return
     */
    //@RequestMapping(value = "/submitBill",method = RequestMethod.POST,produces = "text/html; charset=utf-8")
    @PostMapping(value = "/submitBill")
    public ResponseResult submitBill(){
        ResponseResult responseResult = callFlowService.submitBill();
        return responseResult;
    }

    /**
     * 2.运营科审核
     * @return
     */
    @ResponseBody
    //@RequestMapping(value = "/YunYingKeAudit", produces = "text/html; charset=utf-8")
    @GetMapping(value = "/YunYingKeAudit/{businessKey}")
    public ResponseResult YunYingKeAudit(@PathVariable( "businessKey") String businessKey){
        ResponseResult responseResult = callFlowService.YunYingKeAudit(businessKey);
        return responseResult;
    }

    /**
     * 3.高路公司审核
     * @return
     */
    @ResponseBody
    //@RequestMapping(value = "/gaoLuAudit", produces = "text/html; charset=utf-8")
    @GetMapping(value = "/gaoLuAudit/{businessKey}")
    public ResponseResult gaoLuAudit(@PathVariable( "businessKey") String businessKey){
        ResponseResult responseResult = callFlowService.gaoLuAudit(businessKey);
        return responseResult;
    }

    /**
     * 4.分公司审核
     * @return
     */
    @ResponseBody
    //@RequestMapping(value = "/fenZhongXinAudit",produces = "text/html; charset=utf-8")
    @GetMapping(value = "/fenZhongXinAudit/{businessKey}")
    public ResponseResult fenZhongXinAudit(@PathVariable( "businessKey") String businessKey){
        ResponseResult responseResult = callFlowService.fenZhongXinAudit(businessKey);
        return responseResult;
    }

    /**
     * 5.高路公司的反馈
     * @return
     */
    @ResponseBody
    //@RequestMapping(value = "/gaoluFeedbackAudit", produces = "text/html; charset=utf-8")
    @GetMapping(value = "/gaoluFeedbackAudit/{businessKey}")
    public ResponseResult gaoluFeedbackAudit(@PathVariable( "businessKey") String businessKey){
        ResponseResult responseResult =callFlowService.gaoluFeedbackAudit(businessKey);
        return responseResult;
    }
}
