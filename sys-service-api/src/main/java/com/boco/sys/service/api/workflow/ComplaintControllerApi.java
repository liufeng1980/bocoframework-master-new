package com.boco.sys.service.api.workflow;

import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.JkptTsglOrgrelation;
import com.boco.framework.model.workflow.UploadDocumentItem;
import com.boco.framework.model.workflow.request.Complaint;
import com.boco.framework.model.workflow.request.ComplaintPage;
import com.boco.framework.model.workflow.response.AddFormResponse;
import com.boco.framework.model.workflow.response.ComplaintByTelResponse;
import com.boco.framework.model.workflow.response.JkptTsglOrgRelationExt;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Api(value = "投诉模块", description = "投诉模块相关接口")
public interface ComplaintControllerApi {
    @ApiOperation("添加投诉单")
    ResponseResult addComplaint(@RequestBody Complaint complaint);

    @ApiOperation("初始化添加投诉界面相关参数")
    ResponseResult<AddFormResponse> initAddComplaintPage();

    @ApiOperation("上传文件")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "filePath",value = "文件路径",required = true,paramType = "query",dataType = "String"),
                    @ApiImplicitParam(name = "isCreateFileName",value = "是否生成新的文件名，设置为1",required = true,paramType = "query",dataType = "int")
            }
    )
    ResponseResult<UploadDocumentItem> uploadFile(HttpSession session, HttpServletRequest request,String filePath, int isCreateFileName);

    @ApiOperation("初始化详情界面参数")
    ResponseResult initDetailPage(@RequestBody ComplaintPage complaintPage);

    @ApiOperation("获取分页信息")
    ResponseResult<List<Complaint>> getPage(@RequestBody ComplaintPage complaintListRequest);

    @ApiOperation("根据车牌号获取呼叫流转列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "searchInput",value = "车牌号",required = true,paramType = "path",dataType = "String"),
            }
    )
    ResponseResult<List<ComplaintByTelResponse>> getComplaintByPlateNumOrTel(String searchInput);

    @ApiOperation("获取可以选择的机构列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "complaintParentType",value = "投诉父类型",required = true,paramType = "path",dataType = "String")
            }
    )
    ResponseResult<List<JkptTsglOrgRelationExt>> getReceiveOrgidList(String complaintParentType);


}
