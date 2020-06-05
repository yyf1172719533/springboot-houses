package com.timain.house.mapper;

import com.timain.house.pojo.Agency;
import com.timain.house.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 15:40
 */
@Mapper
public interface AgencyMapper {

    /**
     * 查询经纪机构列表
     * @param agency
     * @return
     */
    List<Agency> selectAgency(Agency agency);

    /**
     * 添加经纪机构
     * @param agency
     * @return
     */
    int insertAgency(Agency agency);

    /**
     * 分页查询经纪机构下的用户列表
     * @param params
     * @return
     */
    List<User> selectAllAgency(Map<String, Object> params);

    /**
     * 查询经纪机构下用户的总数
     * @param user
     * @return
     */
    Long selectCount(User user);
}
