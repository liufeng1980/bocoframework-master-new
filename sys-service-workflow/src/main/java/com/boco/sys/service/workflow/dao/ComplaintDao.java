package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.ComplaintProcessInfo;
import com.boco.framework.model.workflow.JkptTsglOrgrelation;
import com.boco.framework.model.workflow.request.Complaint;
import com.boco.framework.model.workflow.request.ComplaintPage;
import com.boco.framework.model.workflow.request.ComplaintResult;
import com.boco.framework.model.workflow.response.ComplaintByTelResponse;
import com.boco.framework.model.workflow.response.JkptTsglOrgRelationExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ComplaintDao {
    List<ComplaintByTelResponse> getComplaintByPlateNumOrTel(@Param("searchInput") String searchInput);

    List<JkptTsglOrgRelationExt> getReceiveOrgidList(@Param("senderOrgId") String senderOrgId, @Param("complaintParentType") String complaintParentType );

    /**
     * 添加投诉
     * @param complaintResult
     */
    void addComplain(ComplaintResult complaintResult);

    /**
     * 分页查询列表
     * @param complaintPage
     * @return
     */
    List<Complaint> getList(ComplaintPage complaintPage);

    /**
     * 获取基础表详细信息
     * @param complaintId
     * @return
     */
    Complaint getComplaintByComplaintId(@Param("complaintId") Integer complaintId);

    /**
     *获取GroupType
     * @param orgId
     * @param parentType
     * @return
     */
    String getGroupType(String orgId,String parentType);

    /**
     * 查询处理历史
     * @param complaintId
     * @return
     */
    List<ComplaintProcessInfo> queryComplaintProcessHisById(Integer complaintId);

    /**
     * 查询OrgId
     * @param parentType
     * @param groupType
     * @return
     */
    String getOrgId(String parentType,String groupType);

    /**
     * 更新当前处理机构id
     * @param complaintId
     * @param receiverOrgId
     * @return
     */
    int updateCurrentOrgId(@Param("complaintId") Integer complaintId,
                           @Param("receiverOrgId") String receiverOrgId);

    /**
     * 更新受理机构
     * @param acceptOrgid
     * @return
     */
    int updateAcceptOrgId(@Param("complaintId") Integer complaintId,
                          @Param("acceptOrgId") String acceptOrgid);

    /**
     * 更新回访信息
     * @param complaintId
     * @param complaintInfoId
     * @return
     */
    int updateComplaintReturnVisitInfo(@Param("complaintId") Integer complaintId,
                                       @Param("complaintInfoId") Integer complaintInfoId,
                                       @Param("satisfactionStatus") Integer satisfactionStatus,
                                       @Param("senderOrgId") String senderOrgId);

    int updateComplaintInfoReturnVisitInfo(@Param("complaintInfoId") Integer complaintInfoId,
                                           @Param("satisfactionStatus") Integer satisfactionStatus,
                                           @Param("senderOrgId") String senderOrgId,
                                           @Param("senderUserId")  String senderUserId,
                                           @Param("currentStatus") Integer currentStatus,
                                           @Param("suggest") String suggest);

    int updateRecyclebin(@Param("complaintId") Integer complaintId);

    /**
     * 修改投诉管理基表
     * @param complaint
     * @return
     */
    int updateComplaint(Complaint complaint);
}
