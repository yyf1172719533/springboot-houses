package com.timain.house.service;

import com.timain.house.pojo.HouseUser;

import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 11:39
 */
public interface HouseUserService {

    /**
     * 根据条件查询
     * @param params
     * @return
     */
    HouseUser selectHouseUser(Map<String, Object> params);

    /**
     * 添加用户房产信息
     * @param houseUser
     */
    void addHU(HouseUser houseUser);

    /**
     * 查询用户房产信息
     * @param houseId
     * @return
     */
    HouseUser getHouseUser(Long houseId);

    void remHouseUser(Long id, Long userId, Integer type);
}
