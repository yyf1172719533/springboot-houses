package com.timain.house.mapper;

import com.timain.house.pojo.House;
import com.timain.house.pojo.UserMsg;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 9:20
 */
@Mapper
public interface UserMsgMapper {

    /**
     * 添加用户留言
     * @param userMsg
     * @return
     */
    int insertMsg(UserMsg userMsg);

    /**
     * 更改房屋评分
     * @param house
     * @return
     */
    int updateRate(House house);
}
