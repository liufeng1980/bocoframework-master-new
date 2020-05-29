package com.boco.sys.service.ucenter.auth.client;

import com.boco.framework.client.SysServiceList;

import com.boco.framework.model.ucenter.ext.JkptBaseUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = SysServiceList.SYS_SERVICE_UCENTER)
public interface UserClient {
    //根据账号查询用户信息
    @GetMapping("/ucenter/getuserext")
    public JkptBaseUserExt getUserext(@RequestParam("username") String username);
}
