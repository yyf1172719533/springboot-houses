package com.timain.house.service;

import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Community;
import com.timain.house.pojo.House;
import com.timain.house.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 14:16
 */
public interface HouseService {

    /**
     * 查询所有房屋列表
     * @param house
     * @param pageParams
     * @return
     */
    PageData<House> findAll(House house, PageParams pageParams);

    List<House> selectAndSet(House house, PageParams pageParams);

    List<Community> findAll();

    /**
     * 添加房产信息
     * @param house
     * @param user
     */
    void addHouse(House house, User user);

    /**
     * 根据id查询房产信息
     * @param id
     * @return
     */
    House selectOneHouse(Long id);

}
