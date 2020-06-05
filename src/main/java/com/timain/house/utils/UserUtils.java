package com.timain.house.utils;


import com.timain.house.pojo.User;
import com.timain.house.result.ResultMsg;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 21:40
 */
public class UserUtils {

    public static ResultMsg validate(User account){
        if (StringUtils.isBlank(account.getEmail())){
            return ResultMsg.errorMsg("Email有误！");
        }
        if (StringUtils.isBlank(account.getPassWd()) || StringUtils.isBlank(account.getConfirmPassWd()) ||
        !account.getConfirmPassWd().equals(account.getPassWd())){
            return ResultMsg.errorMsg("密码错误！");
        }
        if (account.getPassWd().length() < 6){
            return ResultMsg.errorMsg("密码必须大于6位！");
        }
        return ResultMsg.successMsg("");
    }

    public static ResultMsg validateResetPassword(String key, String password, String confirmPassword) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return ResultMsg.errorMsg("参数错误");
        }
        if (!Objects.equals(password, confirmPassword)) {
            return ResultMsg.errorMsg("密码不一致");
        }
        return ResultMsg.successMsg("");
    }
}
