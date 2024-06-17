package com.fei.service.impl;

import com.fei.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void test() {
        String userAccount = "coffeeMilk";
        String userPassword = "";
        String checkPassword = "123456";

//        long result = userService.register(userAccount, userPassword, checkPassword);
//        Assert.assertEquals(-1, result);
//        userAccount = "co";
//        result = userService.register(userAccount, userPassword, checkPassword);
//        Assert.assertEquals(-1, result);
//        userAccount = "coffeeMilk";
//        userPassword = "123456";
//        result = userService.register(userAccount, userPassword, checkPassword);
//        Assert.assertEquals(-1, result);
//        userPassword = "12345678";
//        checkPassword = "123456789";
//        result = userService.register(userAccount, userPassword, checkPassword);
//        Assert.assertEquals(-1, result);
//        userAccount = "coffee Milk";
//        result = userService.register(userAccount, userPassword, checkPassword);
//        Assert.assertEquals(-1, result);
//        userAccount = "coffee";
//        checkPassword = "12345678";
//        result = userService.register(userAccount, userPassword, checkPassword);
//        Assert.assertEquals(-1, result);
//        userAccount = "coffeeMilk";
//        result = userService.register(userAccount, userPassword, checkPassword);
//        Assert.assertTrue(result > 0);
    }

}