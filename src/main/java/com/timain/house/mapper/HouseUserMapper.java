package com.timain.house.mapper;

import com.timain.house.pojo.House;
import com.timain.house.pojo.HouseUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 11:33
 */
@Mapper
public interface HouseUserMapper {

    HouseUser findOne(Map<String, Object> params);

    int insertHU(HouseUser houseUser);

    HouseUser selectSaleHouseUser(@Param("houseId") Long houseId);

    int deleteHouseUser(Map<String, Object> params);
}
