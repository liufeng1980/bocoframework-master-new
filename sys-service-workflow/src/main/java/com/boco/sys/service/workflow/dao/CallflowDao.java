package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.workflow.Callflow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CallflowDao {
    int deleteByPrimaryKey(Integer keyId);

    int insert(Callflow record);

    int insertSelective(Callflow record);

    Callflow selectByPrimaryKey(Integer keyId);

    int updateByPrimaryKeySelective(Callflow record);

    int updateByPrimaryKey(Callflow record);
}
