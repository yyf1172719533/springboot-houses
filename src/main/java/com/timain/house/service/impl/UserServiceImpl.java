package com.timain.house.service.impl;

import com.google.common.collect.Lists;
import com.timain.house.mapper.UserMapper;
import com.timain.house.pojo.User;
import com.timain.house.service.FileService;
import com.timain.house.service.MailService;
import com.timain.house.service.UserService;
import com.timain.house.utils.BeanHelper;
import com.timain.house.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 22:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private MailService mailService;

    @Value("${file.prefix}")
    private String imgPrefix;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 查询所有用户信息
     *
     * @return
     */
    @Override
    public List<User> selectAll() {
        return userMapper.selectUsers();
    }

    /**
     * 新增用户信息,非激活状态
     * 1.密码使用加盐MD5加密,保存头像文件到本地
     * 2.生成key,绑定email
     * 3.发送邮件到用户email
     * @param account
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccount(User account) {
        account.setPassWd(HashUtils.encryPassword(account.getPassWd()));
        List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
        if (!imgList.isEmpty()){
            account.setAvatar(imgList.get(0));
        }
        BeanHelper.setDefaultProp(account, User.class);
        BeanHelper.onInsert(account);
        account.setEnable(0);
        userMapper.insertUser(account);
        mailService.registerNotify(account.getEmail());
        return true;
    }

    /**
     * 根据条件查询用户信息
     *
     * @param user
     * @return
     */
    @Override
    public List<User> findBySome(User user) {
        List<User> list = userMapper.selectBySome(user);
        list.forEach(u -> {
            u.setAvatar(imgPrefix + u.getAvatar());
        });
        return list;
    }

    /**
     * 判断是否激活成功
     *
     * @param key
     * @return
     */
    @Override
    public boolean enable(String key) {
        return mailService.enable(key);
    }

    /**
     * 用户登录验证
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User auth(String username, String password) {
        User user = new User();
        user.setEmail(username);
        user.setPassWd(HashUtils.encryPassword(password));
        user.setEnable(1);
        List<User> users = findBySome(user);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public void update(User user, String email) {
        user.setEmail(email);
        BeanHelper.onUpdate(user);
        userMapper.updateUser(user);
    }

    /**
     * 发送重置密码邮件
     *
     * @param username
     */
    @Override
    public void resetNotify(String username) {
        mailService.resetNotify(username);
    }

    /**
     * 重置密码
     *
     * @param key
     * @param password
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User reset(String key, String password) {
        String email = getResetEmail(key);
        User updateUser = new User();
        updateUser.setEmail(email);
        updateUser.setPassWd(HashUtils.encryPassword(password));
        userMapper.updateUser(updateUser);
        mailService.invalidateResetKey(key);
        return getUserByEmail(email);
    }

    private User getUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        List<User> userList = userMapper.selectBySome(user);
        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    public String getResetEmail(String key) {
        String email = "";
        try {
            email = mailService.getResetEmail(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return email;
    }

    @Override
    public User findById(Long id) {
        User user = userMapper.getUserById(id);
        return user;
    }

}
