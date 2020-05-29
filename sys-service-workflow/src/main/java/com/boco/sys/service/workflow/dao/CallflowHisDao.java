package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.CallflowHis;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CallflowHisDao {
    int deleteByPrimaryKey(Integer keyId);

    int insert(CallflowHis record);

    int insertSelective(CallflowHis record);

    CallflowHis selectByPrimaryKey(Integer keyId);

    int updateByPrimaryKeySelective(CallflowHis record);

    int updateByPrimaryKey(CallflowHis record);
}
