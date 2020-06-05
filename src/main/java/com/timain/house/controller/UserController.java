package com.timain.house.controller;

import com.timain.house.constants.CommonConstants;
import com.timain.house.pojo.House;
import com.timain.house.pojo.User;
import com.timain.house.result.ResultMsg;
import com.timain.house.service.MailService;
import com.timain.house.service.RecommendService;
import com.timain.house.service.UserService;
import com.timain.house.utils.HashUtils;
import com.timain.house.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/28 21:41
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private RecommendService recommendService;

    /**
     * 用户注册
     * @param account
     * @param modelMap
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountsRegister(User account, ModelMap modelMap) {
        if (null == account || null == account.getName()) {
            return "/user/accounts/register";
        }
        ResultMsg resultMsg = UserUtils.validate(account);
        if (resultMsg.isSuccess() && userService.addAccount(account)) {
            modelMap.put("email", account.getEmail());
            return "/user/accounts/registerSubmit";
        }
        return "redirect:/accounts/register?" + resultMsg.asUrlParams();

    }

    /**
     * 激活链接
     * @param key
     * @return
     */
    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean result = mailService.enable(key);
        if (result) {
            return "redirect:/toIndex?" + ResultMsg.successMsg("激活成功").asUrlParams();
        }
        return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败，请确认链接是否过期");
    }

    /**
     * 转到首页
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(ModelMap modelMap){
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouse);
        return "/homepage/index";
    }

    /**
     * 1.获取用户信息  2.更新用户信息
     * @param request
     * @param updateUser
     * @param modelMap
     * @return
     */
    @RequestMapping("/accounts/profile")
    public String profile(HttpServletRequest request, User updateUser, ModelMap modelMap) {
        if (null==updateUser.getEmail()) {
            return "/user/accounts/profile";
        }
        userService.update(updateUser, updateUser.getEmail());
        User query = new User();
        query.setEmail(updateUser.getEmail());
        List<User> users = userService.findBySome(query);
        request.getSession().setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }

    /**
     * 修改密码
     * @param
     * @param modelMap
     * @return
     */
    @RequestMapping("/accounts/changePassword")
    public String changePassWd(String email, String password, String newPassword, String confirmPassword, ModelMap modelMap) {
        User user = userService.auth(email, password);
        if (null==user || !confirmPassword.equals(newPassword)) {
            return "redirect:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
        }
        User updateUser = new User();
        updateUser.setPassWd(HashUtils.encryPassword(newPassword));
        userService.update(updateUser, email);
        return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
    }

    /**
     * 忘记密码
     * @param username
     * @param modelMap
     * @return
     */
    @RequestMapping("/accounts/remember")
    public String remember(String username, ModelMap modelMap) {
        if (StringUtils.isBlank(username)) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("邮箱不能为空").asUrlParams();
        }
        userService.resetNotify(username);
        modelMap.put("email", username);
        return "/user/accounts/remember";
    }

    @RequestMapping("/accounts/reset")
    public String reset(String key, ModelMap modelMap) {
        String email = userService.getResetEmail(key);
        if (StringUtils.isBlank(email)) {
            return "redirect:/accounts/signin?" + ResultMsg.errorMsg("重置链接已过期").asUrlParams();
        }
        modelMap.put("email", email);
        modelMap.put("success_key", key);
        return "/user/accounts/reset";
    }

    @RequestMapping("/accounts/resetSubmit")
    public String resetSubmit(User user, HttpServletRequest request) {
        ResultMsg resultMsg = UserUtils.validateResetPassword(user.getKey(), user.getPassWd(), user.getConfirmPassWd());
        if (!resultMsg.isSuccess()) {
            String suffix = "";
            if (StringUtils.isNotBlank(user.getKey())) {
                suffix = "email=" + userService.getResetEmail(user.getKey()) + "&key=" + user.getKey();
            }
            return "redirect:/accounts/reset?" + suffix + resultMsg.asUrlParams();
        }
        User updateUser = userService.reset(user.getKey(), user.getPassWd());
        request.getSession().setAttribute(CommonConstants.USER_ATTRIBUTE, updateUser);
        return "redirect:/toIndex";
    }
}

