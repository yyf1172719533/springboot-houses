package com.timain.house.service;

import com.timain.house.pojo.User;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 22:23
 */
public interface UserService {

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> selectAll();

    /**
     * 新增用户信息
     * @param account
     * @return
     */
    boolean addAccount(User account);

    /**
     * 根据条件查询用户信息
     * @param user
     * @return
     */
    List<User> findBySome(User user);

    /**
     * 判断是否激活成功
     * @param key
     * @return
     */
    boolean enable(String key);

    /**
     * 用户登录验证
     * @param username
     * @param password
     * @return
     */
    User auth(String username, String password);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    void update(User user, String email);

    /**
     * 发送重置密码邮件
     * @param username
     */
    void resetNotify(String username);

    /**
     * 重置密码
     * @param key
     * @param password
     * @return
     */
    User reset(String key, String password);

    String getResetEmail(String key);

    User findById(Long id);

}
