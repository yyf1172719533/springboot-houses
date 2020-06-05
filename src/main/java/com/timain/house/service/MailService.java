package com.timain.house.service;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/28 20:23
 */
public interface MailService {

    /**
     * 1.缓存key-email的关系
     * 2.借助spring email发送邮件
     * 3.借助异步框架进行异步操作
     * @param email
     */
    void registerNotify(String email);

    /**
     * 发送邮件
     * @param title
     * @param url
     * @param email
     */
    void sendMail(String title, String url, String email);

    /**
     * 判断该链接是否被激活
     * @param key
     * @return
     */
    boolean enable(String key);

    /**
     * 发送重置密码邮件
     * @param email
     */
    void resetNotify(String email);

    String getResetEmail(String key);

    void invalidateResetKey(String key);
}
