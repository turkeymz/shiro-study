package com.turkeymz.shiro.core.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: cmxu
 * @Description:
 * @Date： create in 16:13 2018/3/3
 * @Modified By:
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @RequestMapping("/userAdd")
    //AOP权限,对应数据库permission字段的值
    @RequiresPermissions("userInfo:add")
    public String userAdd(){
        return "/userInfoAdd";
    }

    @RequestMapping("/userList")
    //AOP权限,对应数据库permission字段的值
    @RequiresPermissions("userInfo:list")
    public String userInfoList(){
        return "/userInfoList";
    }

    @RequestMapping("/userDel")
    //AOP权限,对应数据库permission字段的值
    @RequiresPermissions("userInfo:del")
    public String userDel(){
        return "/userInfoDel";
    }
}
