package com.timain.house.mapper;

import com.timain.house.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 21:24
 */
@Mapper
public interface UserMapper {

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> selectUsers();

    boolean insertUser(User user);

    int deleteUser(String email);

    List<User> selectBySome(User user);

    int updateUser(User user);

    User getUserById(Long id);
}
