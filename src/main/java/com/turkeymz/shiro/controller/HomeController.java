package com.turkeymz.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author : Created by richard (v-rxu@expedia.com)
 * Date : 2018/3/2
 * Time : 10:50
 * Description :
 */
@Controller
public class HomeController {

    @RequestMapping(value = {"/","/index"})
    public String index(){
        return "/index";
    }

    @RequestMapping(value = {"/login"},method = RequestMethod.GET)
    public String login(){
        return "/login";
    }
}
