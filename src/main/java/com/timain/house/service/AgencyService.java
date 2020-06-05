package com.timain.house.service;

import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Agency;
import com.timain.house.pojo.User;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 16:53
 */
public interface AgencyService {

    /**
     * 获取经纪人详情
     * @param userId
     * @return
     */
    User getAgentDetail(Long userId);

    /**
     * 分页查询所有经纪人
     * @param pageParams
     * @return
     */
    PageData<User> getAllAgent(PageParams pageParams);

    /**
     * 根据id查询经纪机构
     * @param id
     * @return
     */
    Agency getAgency(Integer id);

    /**
     * 查询所有经纪机构
     * @return
     */
    List<Agency> findAllAgency();

    /**
     * 添加经纪机构
     * @param agency
     */
    void addAgency(Agency agency);
}
