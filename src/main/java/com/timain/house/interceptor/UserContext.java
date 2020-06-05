package com.timain.house.interceptor;

import com.timain.house.pojo.User;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 10:46
 */
public class UserContext {

    private static final ThreadLocal<User> USER_HODLER = new ThreadLocal<>();

    public static void setUser(User user) {
        USER_HODLER.set(user);
    }

    public static User getUser() {
        return USER_HODLER.get();
    }

    public static void removeUser() {
        USER_HODLER.remove();
    }
}
