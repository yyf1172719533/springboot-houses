package com.timain.house.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * MD5加密工具
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 22:27
 */
public class HashUtils {

    /**
     * 定义函数使用MD5加密
     */
    private static final HashFunction FUNCTION = Hashing.md5();

    /**
     * 加盐
     */
    private static final String SALT = "yyf";

    public static String encryPassword(String password){
        HashCode hashCode = FUNCTION.hashString(password + SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
