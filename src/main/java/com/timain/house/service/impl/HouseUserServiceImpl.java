package com.timain.house.service.impl;

import com.timain.house.mapper.HouseUserMapper;
import com.timain.house.pojo.House;
import com.timain.house.pojo.HouseUser;
import com.timain.house.service.HouseService;
import com.timain.house.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 11:40
 */
@Service
public class HouseUserServiceImpl implements HouseUserService {

    @Autowired
    private HouseUserMapper houseUserMapper;

    /**
     * 根据条件查询
     *
     * @param params
     * @return
     */
    @Override
    public HouseUser selectHouseUser(Map<String, Object> params) {
        HouseUser houseUser = new HouseUser();
        params.put("houseId", houseUser.getHouseId());
        params.put("userId",houseUser.getUserId());
        params.put("type", houseUser.getType());
        return houseUserMapper.findOne(params);
    }

    /**
     * 添加用户房产信息
     *
     * @param houseUser
     */
    @Override
    public void addHU(HouseUser houseUser) {
        houseUserMapper.insertHU(houseUser);
    }

    /**
     * 查询用户房产信息
     *
     * @param houseId
     * @return
     */
    @Override
    public HouseUser getHouseUser(Long houseId) {
        return houseUserMapper.selectSaleHouseUser(houseId);
    }

    @Override
    public void remHouseUser(Long id, Long userId, Integer type) {
        Map<String, Object> params = new HashMap<>();
        params.put("houseId", id);
        params.put("userId", userId);
        params.put("type", type);
        houseUserMapper.deleteHouseUser(params);
    }
}
