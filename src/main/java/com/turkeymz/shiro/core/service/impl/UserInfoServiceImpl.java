package com.turkeymz.shiro.core.service.impl;

import com.turkeymz.shiro.core.bean.UserInfo;
import com.turkeymz.shiro.core.repository.UserInfoRepository;
import com.turkeymz.shiro.core.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: cmxu
 * @Description: 用户逻辑.
 * @Date： create in 16:02 2018/3/3
 * @Modified By:
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Override
	public UserInfo findByUsername(String name) {
		return userInfoRepository.findByUsername(name);
	}

}
