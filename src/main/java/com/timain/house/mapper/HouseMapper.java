package com.timain.house.mapper;

import com.timain.house.pojo.Community;
import com.timain.house.pojo.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 14:16
 */
@Mapper
public interface HouseMapper {

    /**
     * 分页查询所有房屋列表
     * @param params
     * @return
     */
    List<House> selectByPage(Map<String, Object> params);

    /**
     * 查询小区信息
     * @param community
     * @return
     */
    List<Community> selectCommunity(Community community);

    /**
     * 查询房屋总数
     * @param house
     * @return
     */
    Long selectPageCount(@Param("house") House house);

    /**
     * 添加房产信息
     * @param house
     * @return
     */
    int insertHouse(House house);

    House selectOne(House house);

    /**
     * 取消房屋收藏
     * @param id
     * @return
     */
    int remCollHouse(Long id);
}
