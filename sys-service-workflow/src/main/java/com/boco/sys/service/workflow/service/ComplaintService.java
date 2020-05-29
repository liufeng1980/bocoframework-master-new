package com.boco.sys.service.workflow.service;

import com.boco.framework.model.common.JkptCommParamdic;
import com.boco.framework.model.response.ResponseResult;
import com.boco.framework.model.workflow.request.Complaint;
import com.boco.framework.model.workflow.response.AddFormResponse;
import com.boco.framework.model.workflow.response.ComplaintByTelResponse;
import com.boco.framework.model.workflow.response.JkptTsglOrgRelationExt;
import com.boco.sys.service.workflow.dao.ComplaintDao;
import com.boco.sys.service.workflow.dao.JkptCommParamdicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 10:33
 */
@Service
public class ComplaintService {
    @Autowired
    JkptCommParamdicDao paramdicDao;

    @Autowired
    ComplaintDao complaintDao;

    /**
     * 初始化添加界面
     * @return
     */
    public ResponseResult<AddFormResponse> initAddComplaintPage(){
        AddFormResponse addFormResponse = new AddFormResponse();
        List<JkptCommParamdic> parentParams
                = paramdicDao.getParamDicList("ComplaintType", "0");
        addFormResponse.setComplaintTypesParents(parentParams);

        List<JkptCommParamdic> subParams
                = paramdicDao.getParamDicList("ComplaintType","");
        addFormResponse.setComplaintTypesSubs(subParams);

        List<JkptCommParamdic> complaintLevels
                = paramdicDao.getParamDicListByGroupType("ComplaintLevel");
        addFormResponse.setComplaintLevels(complaintLevels);

        List<JkptCommParamdic> whetherOrNotParams
                = paramdicDao.getParamDicListByGroupType("WhetherOrNot");
        addFormResponse.setWeatherOrNot(whetherOrNotParams);

        List<JkptCommParamdic> fileTypes
                = paramdicDao.getParamDicListByGroupType("ComplaintFileType");
        addFormResponse.setComplaintFileTypes(fileTypes);

        List<JkptCommParamdic> idTypes
                = paramdicDao.getParamDicListByGroupType("IdType");
        addFormResponse.setIdTypes(idTypes);

        List<JkptCommParamdic> payTypes
                = paramdicDao.getParamDicListByGroupType("ComplaintPayType");
        addFormResponse.setComplaintEtcPayTypes(payTypes);

        List<JkptCommParamdic> etcAgencyParams
                = paramdicDao.getParamDicListByGroupType("EtcAgency");
        addFormResponse.setComplaintEtcPayTypes(etcAgencyParams);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(new Date());
        addFormResponse.setEventTime(now);
        addFormResponse.setEtcAcceptTime(now);
        addFormResponse.setEtcEventTime(now);

        return   ResponseResult.SUCCESS(addFormResponse);
    }

    /**
     * 按电话或车牌查询列表
     * @param searchInput
     * @return
     */
    public ResponseResult<List<ComplaintByTelResponse>> getComplaintByPlateNumOrTel(String searchInput) {
        List<ComplaintByTelResponse> complaintByPlateNumOrTel = complaintDao.getComplaintByPlateNumOrTel(searchInput);
        return ResponseResult.SUCCESS(complaintByPlateNumOrTel);
    }


    /**
     * 获取可以选择的机构列表
     * @param senderOrgId  发送机构id
     * @param complaintParentType 投诉父类型
     * @return
     */
    public ResponseResult<List<JkptTsglOrgRelationExt>> getReceiveOrgidList(String senderOrgId,String complaintParentType) {
        List<JkptTsglOrgRelationExt> receiveOrgidList = null;
        try{
            receiveOrgidList = complaintDao.getReceiveOrgidList(senderOrgId, complaintParentType);
        }
        catch (Exception e){
            return  ResponseResult.FAIL(e.getMessage());
        }

        return ResponseResult.SUCCESS(receiveOrgidList);
    }
}
