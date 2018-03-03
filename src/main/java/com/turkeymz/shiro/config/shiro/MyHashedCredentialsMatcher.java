package com.turkeymz.shiro.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: cmxu
 * @Description: 自定义凭证匹配器,限制密码次数控制
 * @Date： create in 23:48 2018/3/3
 * @Modified By:
 *
 * 1. 注入缓存对象
 * 2. 获取当前用户账号,username
 * 3. 获取当前账号登录次数,cache<String,AtomicInteger>
 * 4. 大于次数则跑出异常信息,否则进行密码校验
 */
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher{
    //String:userneme,AtomicInteger:retry count
    private Cache<String,AtomicInteger> passwordRetryCache;

    public MyHashedCredentialsMatcher(CacheManager cacheManager){
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 限定次数3次
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //1.获取用户信息
        String username = (String) token.getPrincipal();
        //2.获取输入次数
        AtomicInteger retryCount = passwordRetryCache.get(username);
        //3.是否第一次
        if(retryCount == null){
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username,retryCount);
        }
        //4.判断是否超过3次
        if(retryCount.incrementAndGet() >3){
            throw new ExcessiveAttemptsException();
        }
        //5.进行密码校验
        boolean matches = super.doCredentialsMatch(token,info);
        //6.通过,清除缓存
        if(matches){
            passwordRetryCache.remove(username);
        }
        return super.doCredentialsMatch(token, info);
    }
}
