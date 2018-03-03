package com.turkeymz.shiro.core.service;


import com.turkeymz.shiro.core.bean.UserInfo;

/**
 * @Author: cmxu
 * @Description: 用户逻辑.
 * @Date： create in 16:02 2018/3/3
 * @Modified By:
 */
public interface UserInfoService {
	
	public UserInfo findByUsername(String name);
	
}
