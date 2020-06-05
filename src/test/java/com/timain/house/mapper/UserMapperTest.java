package com.timain.house.mapper;

import com.timain.house.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 22:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectUsers() {
        List<User> userList = userMapper.selectUsers();
        userList.forEach(System.out::println);
    }
}