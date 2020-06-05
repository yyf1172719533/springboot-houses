package com.timain.house.controller;

import com.google.common.base.Objects;
import com.timain.house.constants.CommonConstants;
import com.timain.house.interceptor.UserContext;
import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Agency;
import com.timain.house.pojo.House;
import com.timain.house.pojo.User;
import com.timain.house.result.ResultMsg;
import com.timain.house.service.AgencyService;
import com.timain.house.service.HouseService;
import com.timain.house.service.MailService;
import com.timain.house.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 14:52
 */
@Controller
public class AgencyController {

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private MailService mailService;

    /**
     * 转到创建经纪机构页面
     * @return
     */
    @RequestMapping("/agency/create")
    public String createAgency() {
        User user = UserContext.getUser();
        if (null==user || !Objects.equal(user.getEmail(),"1172719533@qq.com")) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("请先登录").asUrlParams();
        }
        return "/user/agency/create";
    }

    /**
     * 创建经纪机构
     * @param agency
     * @return
     */
    @RequestMapping("/agency/submit")
    public String agencySubmit(Agency agency) {
        User user = UserContext.getUser();
        if (null==user || !Objects.equal(user.getEmail(),"1172719533@qq.com")) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("请先登录").asUrlParams();
        }
        agencyService.addAgency(agency);
        return "redirect:/toIndex";
    }

    /**
     * 经纪人列表
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/agency/agentList")
    public String agentList(Integer pageNum, Integer pageSize, ModelMap modelMap) {
        if (null==pageSize) {
            pageSize = 6;
        }
        User user = UserContext.getUser();
        if (null==user) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("请先登录").asUrlParams();
        }
        PageData<User> allAgent = agencyService.getAllAgent(PageParams.build(pageNum, pageSize));
        /*allAgent.getList().forEach(System.out::println);*/
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("ps", allAgent);
        return "/user/agent/agentList";
    }

    /**
     * 经纪人详情
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/agency/agentDetail")
    public String agentDetail(Long id, ModelMap modelMap) {
        User user = agencyService.getAgentDetail(id);
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        House house = new House();
        house.setUserId(id);
        house.setBookmarked(false);
        PageData<House> pageData = houseService.findAll(house, new PageParams(3, 1));
        if (null!=pageData) {
            modelMap.put("bindHouses", pageData.getList());
        }
        modelMap.put("recomHouses",hotHouse);
        modelMap.put("agent",user);
        modelMap.put("agencyName",user.getAgencyName());
        return "/user/agent/agentDetail";
    }

    /**
     * 给经纪人留言
     * @param id
     * @param name
     * @param msg
     * @param email
     * @param modelMap
     * @return
     */
    @RequestMapping("/agency/agentMsg")
    public String agencyMsg(Long id, String name, String msg, String email, ModelMap modelMap) {
        User user = agencyService.getAgentDetail(id);
        mailService.sendMail("咨询","email:" + email + ",msg:" + msg, user.getEmail());
        return "redirect:/agency/agentDetail?id=" + id + "&" + ResultMsg.successMsg("留言成功").asUrlParams();
    }

    /**
     * 经纪机构列表
     * @param modelMap
     * @return
     */
    @RequestMapping("/agency/list")
    public String agencyList(ModelMap modelMap) {
        List<Agency> agencyList = agencyService.findAllAgency();
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouse);
        modelMap.put("agencyList",agencyList);
        return "/user/agency/agencyList";
    }

    /**
     * 经纪机构详情
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/agency/agencyDetail")
    public String agencyDetail(Integer id, ModelMap modelMap) {
        Agency agency = agencyService.getAgency(id);
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses",hotHouse);
        modelMap.put("agency",agency);
        return "/user/agency/agencyDetail";
    }
}
