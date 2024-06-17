package com.fei.service;

import com.fei.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author fency
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-06-13 12:00:38
*/
public interface UserService extends IService<User> {


    /**
     *  用户注册
     * @param userAccount 账号名
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @return 新用户的ID
     */
    public Long register(String userAccount, String userPassword,String checkPassword,String planetCode);

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request 浏览器请求
     * @return 返回登录态
     */
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     * @param request 脱敏
     * @return 注销用户的id
     */
    public Integer userLogout(HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originalUser 数据库表取到的信息
     * @return 脱敏后信息
     */
    User safeUser(User originalUser);
}


