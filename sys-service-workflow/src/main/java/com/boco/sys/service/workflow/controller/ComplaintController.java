package com.boco.sys.service.workflow.controller;

import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.JkptTsglOrgrelation;
import com.boco.framework.model.workflow.UploadDocumentItem;
import com.boco.framework.model.workflow.request.*;
import com.boco.framework.model.workflow.response.AddFormResponse;
import com.boco.framework.model.workflow.response.ComplaintByTelResponse;
import com.boco.framework.model.workflow.response.DetailFormResponse;
import com.boco.framework.model.workflow.response.JkptTsglOrgRelationExt;
import com.boco.framework.web.BaseController;
import com.boco.sys.service.api.workflow.ComplaintControllerApi;
import com.boco.sys.service.workflow.service.ComplaintService;
import com.boco.utils.SysOauth2Util;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ComplaintController extends BaseController implements ComplaintControllerApi {
    @Autowired
    ComplaintService complaintService;

    @PostMapping("/add_complaint")
    @Override
    public ResponseResult addComplaint(@RequestBody Complaint complaint) {
        SysOauth2Util sysOauth2Util = new SysOauth2Util();
        SysOauth2Util.UserJwt userJwt = sysOauth2Util.getUserJwtFromHeader(request);
        ResponseResult responseResult;
        try {
            responseResult =complaintService.addComplaint(userJwt,complaint);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = ResponseResult.FAIL(e.getMessage());
        }
        return responseResult;
    }

    @GetMapping("/init_add_complaint_page")
    @Override
    public ResponseResult<AddFormResponse> initAddComplaintPage() {
        ResponseResult<AddFormResponse> result = complaintService.initAddComplaintPage();
        return result;
    }

    @PostMapping("/uploadFile")
    @Override
    public ResponseResult<UploadDocumentItem> uploadFile(HttpSession session, HttpServletRequest request, String filePath, int isCreateFileName) {
        return null;
    }

    @PostMapping("/getPage")
    @Override
    public ResponseResult<PageInfo<Complaint>> getPage(@RequestBody ComplaintPage complaintListRequest) {
        SysOauth2Util sysOauth2Util = new SysOauth2Util();
        SysOauth2Util.UserJwt userJwt = sysOauth2Util.getUserJwtFromHeader(request);
        PageInfo<Complaint> page = complaintService.getPage(userJwt, complaintListRequest);
        return ResponseResult.SUCCESS(page);
    }

    @GetMapping("/getComplaintByPlateNumOrTel/{searchInput}")
    @Override
    public ResponseResult<List<ComplaintByTelResponse>> getComplaintByPlateNumOrTel(@PathVariable("searchInput") String searchInput) {
        ResponseResult<List<ComplaintByTelResponse>> result =
                complaintService.getComplaintByPlateNumOrTel(searchInput);
        return result;
    }

    @GetMapping("/getReceiveOrgidList/{complaintParentType}")
    @Override
    public ResponseResult<List<JkptTsglOrgRelationExt>> getReceiveOrgidList(@PathVariable("complaintParentType") String complaintParentType) {
        SysOauth2Util sysOauth2Util = new SysOauth2Util();
        SysOauth2Util.UserJwt userJwt = sysOauth2Util.getUserJwtFromHeader(request);
        ResponseResult<List<JkptTsglOrgRelationExt>> receiveOrgidList = complaintService.getReceiveOrgidList(userJwt.getOrgid(), complaintParentType);
        return receiveOrgidList;
    }

    @PostMapping("/initDetailPage")
    @Override
    public ResponseResult<DetailFormResponse> initDetailPage(@RequestBody DetailFormRequest detailFormRequest){
        SysOauth2Util sysOauth2Util = new SysOauth2Util();
        SysOauth2Util.UserJwt userJwt = sysOauth2Util.getUserJwtFromHeader(request);
        ResponseResult<DetailFormResponse> result = complaintService.initDetailPage(userJwt, detailFormRequest);
        return result;
    }

    @GetMapping("/test/{key}")
    @Override
    public ResponseResult test(@PathVariable("key") String key) {
        complaintService.test(key);
        return null;
    }

    @PostMapping("/audit")
    @Override
    public ResponseResult audit(ProcessDetailRequest processDetailRequest) {
        SysOauth2Util sysOauth2Util = new SysOauth2Util();
        SysOauth2Util.UserJwt userJwt = sysOauth2Util.getUserJwtFromHeader(request);

        ResponseResult result = complaintService.audit(userJwt,processDetailRequest);
        return result;
    }

    @GetMapping("/deployTest")
    public ResponseResult deployTest(){
        return complaintService.deployTest();
    }

    @GetMapping("/deployComplaint")
    public ResponseResult deployComplaint(){
        return complaintService.deployComplaint();
    }

    @PostMapping("/doReturnVisit")
    @Override
    public ResponseResult doReturnVisit( ReturnVisitRequest returnVisitRequest){
        SysOauth2Util sysOauth2Util = new SysOauth2Util();
        SysOauth2Util.UserJwt userJwt = sysOauth2Util.getUserJwtFromHeader(request);

        return complaintService.doReturnVisit(userJwt,returnVisitRequest);
    }
}
