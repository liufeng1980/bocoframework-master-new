package com.boco.sys.service.workflow.dao;

import com.boco.framework.model.common.JkptCommParamdic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Liu Feng
 * @Date: 2020-5-29 10:15
 */
@Mapper
public interface JkptCommParamdicDao {
    /**
     *
     * @param groupType  组类型
     * @param dicParentId 父类型
     * @return
     */
    List<JkptCommParamdic> getParamDicList(@Param("groupType") String groupType,
                                           @Param("dicParentId")  String dicParentId);

    /**
     *
     * @param groupType  组类型
     * @return
     */
    List<JkptCommParamdic> getParamDicListByGroupType(@Param("groupType") String groupType);
}
