package com.timain.house.mapper;

import com.timain.house.pojo.Community;
import com.timain.house.pojo.House;
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
 * @date 2020/1/2 10:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseMapperTest {

    @Autowired
    private HouseMapper houseMapper;

    @Test
    public void selectCommunity() {
        List<House> houseList = houseMapper.selectByPage(null);
        if (!houseList.isEmpty()) {
            Community community = new Community();
            community.setName(houseList.get(0).getName());
            List<Community> communityList = houseMapper.selectCommunity(community);
            communityList.forEach(System.out::println);
        }
    }
}