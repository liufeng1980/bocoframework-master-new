package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglAuditinfo;
import com.boco.framework.model.workflow.response.JkptTsglAuditInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface JkptTsglAuditinfoExtDao {
    JkptTsglAuditInfoResponse queryComplaintAuditInfo(@Param("auditInfoId") Integer auditInfoId);

    /**
     * 更新投诉信息表状态为不满意
     *
     * @param complaintAuditInfoId
     * @param currentSuggest
     * @param currentStateDesc
     * @param statifyState
     * @param sendUserId
     * @return
     */
    int updateUnStatisfiyStatusWithAuditInfo(@Param("complaintAuditInfoId") Integer complaintAuditInfoId,
                                             @Param("currentSuggest") String currentSuggest,
                                             @Param("currentStateDesc") String currentStateDesc,
                                             @Param("statifyState") Integer statifyState,
                                             @Param("sendUserId") String sendUserId);

    /**
     * 获取最大不满意数（比当前值大1)
     *
     * @param complaintId
     * @return
     */
    Integer getComplaintFrequency(@Param("complaintId") Integer complaintId);

    /**
     * 更新回收站状态
     *
     * @param sendUserId
     * @param sendOrgId
     * @param suggest
     * @return
     */
    int updateAuditinfoRecyclebinStatus(@Param("complaintInfoId") Integer complaintInfoId,
                                        @Param("sendUserId") String sendUserId,
                                        @Param("sendOrgId") String sendOrgId,
                                        @Param("suggest") String suggest,
                                        @Param("statusDesc") String statusDesc);
}
