package com.boco.sys.service.ucenter.controller;

import com.boco.framework.model.ucenter.ext.JkptBaseUserExt;
import com.boco.sys.service.api.usermanager.UserControllerApi;
import com.boco.sys.service.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UserControllerApi {
    @Autowired
    UserService userService;
    @Override
    @GetMapping("/getuserext")
    public JkptBaseUserExt getUserByName(@RequestParam("username") String username) {
        return userService.findUserExt(username);
    }



}
