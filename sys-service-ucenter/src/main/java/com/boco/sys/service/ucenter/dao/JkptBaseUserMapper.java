package com.boco.sys.service.ucenter.dao;

import com.boco.framework.model.ucenter.JkptBaseUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JkptBaseUserMapper {
    JkptBaseUser findUserByName(String name);
}
