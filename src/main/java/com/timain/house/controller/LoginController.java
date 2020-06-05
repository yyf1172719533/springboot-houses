package com.timain.house.controller;

import com.timain.house.constants.CommonConstants;
import com.timain.house.pojo.User;
import com.timain.house.result.ResultMsg;
import com.timain.house.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 9:30
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param request
     * @return
     */
    @RequestMapping("/accounts/signin")
    public String login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String target = request.getParameter("target");
        if (null==username || null==password) {
            return "/user/accounts/signin";
        }
        User user = userService.auth(username, password);
        if (null==user) {
            return "redirect:/accounts/signin?" + "target=" +target + "&username=" + username +
                    "&" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
        }
        request.getSession().setAttribute(CommonConstants.USER_ATTRIBUTE,user);
        return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/toIndex";
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/toIndex";
    }
}
