package com.timain.house.service.impl;

import com.timain.house.mapper.UserMsgMapper;
import com.timain.house.pojo.House;
import com.timain.house.pojo.User;
import com.timain.house.pojo.UserMsg;
import com.timain.house.service.AgencyService;
import com.timain.house.service.HouseService;
import com.timain.house.service.MailService;
import com.timain.house.service.UserMsgService;
import com.timain.house.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 9:25
 */
@Service
public class UserMsgServiceImpl implements UserMsgService {

    @Autowired
    private UserMsgMapper userMsgMapper;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private MailService mailService;
    @Autowired
    private HouseService houseService;

    /**
     * 添加用户留言
     *
     * @param userMsg
     */
    @Override
    public void addUserMsg(UserMsg userMsg) {
        BeanHelper.onInsert(userMsg);
        userMsgMapper.insertMsg(userMsg);
        User user = agencyService.getAgentDetail(userMsg.getAgentId());
        mailService.sendMail("来自用户" + userMsg.getEmail() + "的留言", userMsg.getMsg(), user.getEmail());
    }

    /**
     * 更改房屋评分
     *
     * @param id
     * @param rating
     */
    @Override
    public void updateRating(Long id, Double rating) {
        House house = houseService.selectOneHouse(id);
        Double oldRating = house.getRating();
        Double newRating = oldRating.equals(0D) ? rating : Math.min((oldRating+rating)/2, 5);
        House updateHouse = new House();
        updateHouse.setId(id);
        updateHouse.setRating(newRating);
        BeanHelper.onUpdate(updateHouse);
        userMsgMapper.updateRate(updateHouse);
    }

}
