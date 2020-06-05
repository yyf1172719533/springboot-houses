package com.timain.house.controller;

import com.timain.house.constants.CommonConstants;
import com.timain.house.constants.HouseUserType;
import com.timain.house.interceptor.UserContext;
import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Comment;
import com.timain.house.pojo.House;
import com.timain.house.pojo.HouseUser;
import com.timain.house.pojo.User;
import com.timain.house.service.*;
import com.timain.house.service.impl.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/30 16:13
 */
@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private CityService cityService;
    @Autowired
    private HouseUserService houseUserService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private HouseServiceImpl houseServiceImpl;

    /**
     * 查询房屋列表
     * @param pageNum
     * @param pageSize
     * @param house
     * @param modelMap
     * @return
     */
    @RequestMapping("/house/list")
    public String findList(Integer pageNum, Integer pageSize, House house, ModelMap modelMap){
        PageData<House> data = houseService.findAll(house, PageParams.build(pageNum, pageSize));
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouse);
        modelMap.put("ps",data);
        modelMap.put("vo",house);
        return "house/listing";
    }

    /**
     * 获取房产详情信息
     * 查询房产关联经纪人信息
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/house/detail")
    public String houseDetail(Long id, ModelMap modelMap){
        House house = houseService.selectOneHouse(id);
        HouseUser houseUser = houseUserService.getHouseUser(id);
        recommendService.increase(id);
        List<Comment> houseComments = commentService.getHouseComments(id);
        if (null!=houseUser.getUserId() && !houseUser.getUserId().equals(0)) {
            modelMap.put("agent", agencyService.getAgentDetail(houseUser.getUserId()));
        }
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("house", house);
        modelMap.put("commentList", houseComments);
        return "/house/detail";
    }

    /**
     * 查询城市和社区信息并转到添加房产页面
     * @param modelMap
     * @return
     */
    @RequestMapping("/house/toAdd")
    public String toAdd(ModelMap modelMap) {
        modelMap.put("citys", cityService.getAllCitys());
        modelMap.put("communitys", houseService.findAll());
        return "/house/add";
    }

    /**
     * 添加房产信息
     * @param house
     * @return
     */
    @RequestMapping("/house/add")
    public String addHouse(House house) {
        User user = UserContext.getUser();
        house.setState(CommonConstants.HOUSE_STATE_UP);
        houseService.addHouse(house, user);
        return "redirect:/house/ownlist";
    }

    /**
     * 用户个人房产信息
     * @param house
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/house/ownlist")
    public String ownList(House house,Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setUserId(user.getId());
        house.setBookmarked(false);
        modelMap.put("ps",houseService.findAll(house, PageParams.build(pageNum, pageSize)));
        modelMap.put("pageType","own");
        return "/house/ownlist";
    }

    /**
     * 删除个人房产信息
     * @param id
     * @param pageType
     * @return
     */
    @RequestMapping("/house/del")
    public String delSale(Long id, String pageType) {
        User user = UserContext.getUser();
        houseServiceImpl.unBindUserAndHouse(id, user.getId(), pageType.equals("own") ? HouseUserType.SALE : HouseUserType.BOOKMARK);
        return "redirect:/house/ownlist";
    }

    /**
     * 房屋收藏列表
     * @return
     */
    @RequestMapping("/house/bookmarked")
    public String bookMarked(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        User user = UserContext.getUser();
        house.setBookmarked(true);
        house.setUserId(user.getId());
        modelMap.put("ps", houseService.findAll(house, PageParams.build(pageNum, pageSize)));
        modelMap.put("pageType", "book");
        return "/house/ownlist";
    }
}
