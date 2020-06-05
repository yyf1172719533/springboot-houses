package com.timain.house.controller;

import com.timain.house.constants.HouseUserType;
import com.timain.house.interceptor.UserContext;
import com.timain.house.pojo.User;
import com.timain.house.pojo.UserMsg;
import com.timain.house.result.ResultMsg;
import com.timain.house.service.UserMsgService;
import com.timain.house.service.impl.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 9:30
 */
@Controller
public class UserMsgController {

    @Autowired
    private UserMsgService userMsgService;
    @Autowired
    private HouseServiceImpl houseServiceImpl;

    /**
     * 添加用户留言
     * @return
     */
    @RequestMapping("/house/leaveMsg")
    public String userMsg(UserMsg userMsg) {
        userMsgService.addUserMsg(userMsg);
        return "redirect:/house/detail?id=" + userMsg.getHouseId() + "&" + ResultMsg.successMsg("留言成功").asUrlParams();
    }

    /**
     * 评分
     * @param id
     * @param rating
     * @return
     */
    @RequestMapping("/house/rating")
    @ResponseBody
    public ResultMsg houseRate(Long id, Double rating) {
        userMsgService.updateRating(id, rating);
        return ResultMsg.successMsg("ok");
    }

    /**
     * 加入收藏
     * @param id
     * @return
     */
    @RequestMapping("/house/bookmark")
    @ResponseBody
    public ResultMsg bookMark(Long id) {
        User user = UserContext.getUser();
        houseServiceImpl.bindUserAndHouse(id, user.getId(), true);
        return ResultMsg.successMsg("ok");
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @RequestMapping("/house/unbookmark")
    @ResponseBody
    public ResultMsg unBookMark(Long id) {
        User user = UserContext.getUser();
        houseServiceImpl.unBindUserAndHouse(id, user.getId(), HouseUserType.BOOKMARK);
        return ResultMsg.successMsg("ok");
    }
}
