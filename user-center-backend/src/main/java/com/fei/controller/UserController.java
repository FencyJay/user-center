package com.fei.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fei.common.BaseResponse;
import com.fei.common.ErrorCode;
import com.fei.common.ResultUtil;
import com.fei.exception.BusinessException;
import com.fei.model.domain.User;
import com.fei.model.request.UserLoginRequest;
import com.fei.model.request.UserRegisterRequest;
import com.fei.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.fei.constant.UserConstant.ADMIN_ROLE;
import static com.fei.constant.UserConstant.USER_LOGIN_STATUS;

@RestController
@RequestMapping("/user")

public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = userService.register(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtil.success(userId);
    }


    @PostMapping("/login")
    public BaseResponse<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtil.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        if (request == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer userId = userService.userLogout(request);
        return ResultUtil.success(userId);
    }


    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw  new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Long userId = currentUser.getId();
        //TODO 校验用户是否合法
        User user = userService.getById(userId);
        User result = userService.safeUser(user);
        return ResultUtil.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List> searchUsers(String username, HttpServletRequest request) {
        //管理员可查询
        if (!isAdmin(request)) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.eq("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> {
            userService.safeUser(user);
            return user;
        }).collect(Collectors.toList());
        //脱敏返回
        return ResultUtil.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(long id, HttpServletRequest request) {
        //管理员可删除
        if (!isAdmin(request)) {
            throw  new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            return null;
        }
        boolean b = userService.removeById(id);
        return ResultUtil.success(b);
    }


    /**
     * 鉴权是否是管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        //管理员可删除
        Object userObje = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObje;
        return user != null && user.getUserRole() == ADMIN_ROLE;

    }
}
