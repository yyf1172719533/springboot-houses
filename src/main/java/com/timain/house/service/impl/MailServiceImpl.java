package com.timain.house.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.timain.house.mapper.UserMapper;
import com.timain.house.pojo.User;
import com.timain.house.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/28 20:25
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${domain.name}")
    private String domainName;
    @Value("${spring.mail.username}")
    private String from;

    private final Cache<String, String> resetCache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES).build();

    private final Cache<String, String> registerCache = CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, String>() {

                /**
                 * Notifies the listener that a removal occurred at some point in the past.
                 *
                 * <p>This does not always signify that the key is now absent from the cache, as it may have
                 * already been re-added.
                 *
                 * @param notification
                 */
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {
                    String email = notification.getValue();
                    User user = new User();
                    user.setEmail(email);
                    List<User> userList = userMapper.selectBySome(user);
                    //删除之前先判断用户是否被激活
                    if (!userList.isEmpty() && Objects.equals(userList.get(0).getEnable(),0)) {
                        userMapper.deleteUser(email);
                    }
                }
            }).build();

    /**
     * 1.缓存key-email的关系
     * 2.借助spring email发送邮件
     * 3.借助异步框架进行异步操作
     *
     * @param email
     */
    @Async
    @Override
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey, email);
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        sendMail("房屋平台激活邮件", url, email);
    }

    /**
     * 发送邮件
     *
     * @param title
     * @param url
     * @param email
     */
    @Async
    @Override
    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(title);
        message.setTo(email);
        message.setText(url);
        mailSender.send(message);
    }

    /**
     * 判断该链接是否被激活
     *
     * @param key
     * @return
     */
    @Override
    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setEnable(1);
        userMapper.updateUser(updateUser);
        registerCache.invalidate(key);
        return true;
    }

    /**
     * 发送重置密码邮件
     *
     * @param email
     */
    @Async
    @Override
    public void resetNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        resetCache.put(randomKey, email);
        String content = "http://" + domainName + "/accounts/reset?key=" + randomKey;
        sendMail("房产平台密码重置邮件", content, email);
    }

    public String getResetEmail(String key) {
        return resetCache.getIfPresent(key);
    }

    public void invalidateResetKey(String key) {
        resetCache.invalidate(key);
    }
}
