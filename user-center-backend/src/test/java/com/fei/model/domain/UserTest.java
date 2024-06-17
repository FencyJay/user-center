package com.fei.model.domain;

import com.fei.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 用户服务测试
 *
 * @author fency
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        User user = new User();

        user.setUsername("coffee");
        user.setUserAccount("123");
        user.setAvatarUrl("https://tu.sioe.cn/gj/qiege/image.jpg");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        user.setUserStatus(0);
        boolean result = userService.save(user);
        assertTrue(result);
        System.out.println(result);
    }

}