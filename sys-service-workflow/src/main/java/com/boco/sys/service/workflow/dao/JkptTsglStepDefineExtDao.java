package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.JkptTsglStepDefine;
import org.apache.ibatis.annotations.Param;

public interface JkptTsglStepDefineExtDao {
    JkptTsglStepDefine query(@Param("sender") String sender,
                             @Param("receiver") String receiver,
                             @Param("complaintParentType") String complaintParentType);
}
