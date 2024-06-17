package com.fei;

import com.fei.service.UserService;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

@SpringBootTest
class UserCenterBackendApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {



    }

}
