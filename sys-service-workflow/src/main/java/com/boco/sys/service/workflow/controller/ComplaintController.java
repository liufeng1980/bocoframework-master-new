package com.boco.sys.service.workflow.controller;

import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.JkptTsglOrgrelation;
import com.boco.framework.model.workflow.UploadDocumentItem;
import com.boco.framework.model.workflow.request.Complaint;
import com.boco.framework.model.workflow.request.ComplaintPage;
import com.boco.framework.model.workflow.response.AddFormResponse;
import com.boco.framework.model.workflow.response.ComplaintByTelResponse;
import com.boco.framework.model.workflow.response.JkptTsglOrgRelationExt;
import com.boco.framework.web.BaseController;
import com.boco.sys.service.api.workflow.ComplaintControllerApi;
import com.boco.sys.service.workflow.service.ComplaintService;
import com.boco.utils.SysOauth2Util;
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
        return ResponseResult.SUCCESS();
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

    @PostMapping("/initDetailPage")
    @Override
    public ResponseResult initDetailPage(ComplaintPage complaintPage) {
        return null;
    }

    @PostMapping("/getPage")
    @Override
    public ResponseResult<List<Complaint>> getPage(@RequestBody ComplaintPage complaintListRequest) {
        return null;
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


}
