package com.boco.framework.model.ucenter.ext;

import com.boco.framework.model.ucenter.JkptBaseUser;
import com.boco.framework.model.ucenter.JkptSysRight;
import lombok.Data;

import java.util.List;

@Data
public class JkptBaseUserExt extends JkptBaseUser {
    //权限信息
    private List<JkptSysRight> permissions;
}
