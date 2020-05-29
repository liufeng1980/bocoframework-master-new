package com.boco.sys.service.ucenter.service;

import com.boco.framework.model.ucenter.JkptBaseUser;
import com.boco.framework.model.ucenter.JkptSysRight;
import com.boco.framework.model.ucenter.ext.JkptBaseUserExt;
import com.boco.sys.service.ucenter.dao.JkptBaseUserMapper;
import com.boco.sys.service.ucenter.dao.JkptSysRightMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
//    @Autowired
//    private MenuMapper menuMapper;
//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private JkptBaseUserMapper jkptBaseUserMapper;

    @Autowired
    private JkptSysRightMapper jkptSysRightMapper;

    public JkptBaseUser findUserByUsername(String username){
        return jkptBaseUserMapper.findUserByName(username);
    }

    public JkptBaseUserExt findUserExt(String username){
        JkptBaseUser userByUsername = this.findUserByUsername(username);
        if(userByUsername == null){
            return null;
        }
        //用户id
        String userId = userByUsername.getLoginid();
        //查询用户所有权限
        List<JkptSysRight> jkptSysRights = jkptSysRightMapper.querySysRightsByName(userId);

        JkptBaseUserExt userExt = new JkptBaseUserExt();
        BeanUtils.copyProperties(userByUsername,userExt);
        //xcUserExt.setCompanyId(companyId);
        //设置权限
        userExt.setPermissions(jkptSysRights);
        return userExt;
    }

    //根据账号查询User信息
//    public User findXcUserByUsername(String username){
//        return userMapper.findByUsername(username);
//    }
//
//    //根据账号查询用户信息
//    public UserExt getUserExt(String username){
//        //根据账号查询xcUser信息
//        User xcUser = this.findXcUserByUsername(username);
//        if(xcUser == null){
//            return null;
//        }
//        //用户id
//        String userId = xcUser.getId();
//        //查询用户所有权限
//        List<Menu_del> xcMenus = menuMapper.selectPermissionByUserId(userId);
//
//        UserExt xcUserExt = new UserExt();
//        BeanUtils.copyProperties(xcUser,xcUserExt);
//        //xcUserExt.setCompanyId(companyId);
//        //设置权限
//        xcUserExt.setPermissions(xcMenus);
//        return xcUserExt;
//    }


}
