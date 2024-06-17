package com.fei.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fei.common.ErrorCode;
import com.fei.exception.BusinessException;
import com.fei.model.domain.User;
import com.fei.service.UserService;
import com.fei.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fei.constant.UserConstant.USER_LOGIN_STATUS;

/**
* @author Lenovo
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-06-13 12:00:38
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private  UserMapper userMapper;

    public static final String SALT = "coffee";



    /**
     *  用户注册
     * @param userAccount 账号名
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @return 新用户的ID
     */
    @Override
    public Long register(String userAccount, String userPassword, String checkPassword,String planetCode) {
        //非空校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if(userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户长度不能小于4");
        }
        if(userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度不能小于8");
        }
        if (planetCode.length() > 5 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号不能大于5");
        }

        boolean specialChar = hasSpecialChar(userAccount);
        if(!specialChar){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能包含特殊字符");
        }


        // 密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不一致");
        }

        //账户不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount)
                    .or()
                    .eq("planetCode", planetCode);
        Long count = userMapper.selectCount(queryWrapper);
        if(count != 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号已存在");
        }


        //加密
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());



        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if(!saveResult){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"注册失败");
        }

        return user.getId();
    }


    public User userLogin(String userAccount, String userPassword,HttpServletRequest request) {
        //校验账号和密码
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码不能为空");
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度小于4");
        }
        if(userPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度小于8");
        }
        boolean specialChar = hasSpecialChar(userAccount);
        if(!specialChar){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能包含特殊字符");
        }


        // 校验账号密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号密码不正确");
        }

        //用户信息脱敏
        User safeUser = safeUser(user);


        // 记录登录态
        request.getSession().setAttribute(USER_LOGIN_STATUS,user);

        return safeUser;
    }

    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 0;
    }

    public User safeUser(User user){
        User safeUser = new User();
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不正确");
        }
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setPlanetCode(user.getPlanetCode());
        return safeUser;
    }


    public static boolean hasSpecialChar(String userAccount) {
        // 账号不能包含特殊字符
        String regex = "^[a-zA-Z0-9]+$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userAccount);
        if(!matcher.matches()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号不能包含特殊字符");
        }
        return true;
    }

}




