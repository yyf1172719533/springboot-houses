package com.timain.house.service;

import com.timain.house.pojo.House;
import com.timain.house.pojo.UserMsg;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 9:24
 */
public interface UserMsgService {

    /**
     * 添加用户留言
     * @param userMsg
     */
    void addUserMsg(UserMsg userMsg);

    /**
     * 更改房屋评分
     * @param id
     * @param rating
     */
    void updateRating(Long id, Double rating);
}
