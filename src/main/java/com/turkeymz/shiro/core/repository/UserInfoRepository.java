package com.turkeymz.shiro.core.repository;


import com.turkeymz.shiro.core.bean.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author: cmxu
 * @Description: 持久层.
 * @Date： create in 16:02 2018/3/3
 * @Modified By:
 */
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
	
	//通过用户名查找用户信息.
	public UserInfo findByUsername(String name);
}
