package com.boco.sys.service.ucenter.dao;

import com.boco.framework.model.ucenter.JkptSysRight;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JkptSysRightMapper {
    List<JkptSysRight> querySysRightsByName(String name);
}
