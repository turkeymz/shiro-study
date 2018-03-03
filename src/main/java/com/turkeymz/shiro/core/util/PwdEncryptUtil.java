package com.turkeymz.shiro.core.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Author: cmxu
 * @Description: 密码加密
 * @Date： create in 22:59 2018/3/3
 * @Modified By:
 */
public class PwdEncryptUtil {

    private static String salt = "8d78869f470951332959580424d4bf4f";

    public String encrypt(String username,String password){
        String saltStr = username + salt;
        //密码、盐、散列次数
        Md5Hash md5Hash = new Md5Hash(password,saltStr,2);
        return md5Hash.toString();
    }
}
