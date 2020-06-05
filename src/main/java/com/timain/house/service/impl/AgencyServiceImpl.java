package com.timain.house.service.impl;

import com.timain.house.mapper.AgencyMapper;
import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Agency;
import com.timain.house.pojo.User;
import com.timain.house.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 16:59
 */
@Service
public class  AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 获取经纪人详情
     *
     * @param userId
     * @return
     */
    @Override
    public User getAgentDetail(Long userId) {
        /*User user = new User();
        user.setId(userId);
        user.setType(2);*/
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        params.put("pageParams",PageParams.build(1,1));
        List<User> userList = agencyMapper.selectAllAgency(params);
        setImg(userList);
        if (!userList.isEmpty()) {
            User agent = userList.get(0);
            Agency agency = new Agency();
            agency.setId(agent.getAgencyId().intValue());
            List<Agency> agencies = agencyMapper.selectAgency(agency);
            if (!agencies.isEmpty()) {
                agent.setAgencyName(agencies.get(0).getName());
            }
            return agent;
        }
        return null;
    }

    /**
     * 分页查询所有经纪人
     *
     * @param pageParams
     * @return
     */
    @Override
    public PageData<User> getAllAgent(PageParams pageParams) {
        Map<String, Object> map = new HashMap<>();
        map.put("user",new User());
        map.put("pageParams", pageParams);
        List<User> userList = agencyMapper.selectAllAgency(map);
        Long count = agencyMapper.selectCount(new User());
        setImg(userList);
        return PageData.buildPage(pageParams.getPageNum(), pageParams.getPageSize(), count, userList);
    }

    /**
     * 根据id查询经纪机构
     *
     * @param id
     * @return
     */
    @Override
    public Agency getAgency(Integer id) {
        Agency agency = new Agency();
        agency.setId(id);
        List<Agency> agencies = agencyMapper.selectAgency(agency);
        if (!agencies.isEmpty()) {
            return agencies.get(0);
        }
        return null;
    }

    /**
     * 查询所有经纪机构
     *
     * @return
     */
    @Override
    public List<Agency> findAllAgency() {
        return agencyMapper.selectAgency(new Agency());
    }

    /**
     * 添加经纪机构
     *
     * @param agency
     */
    @Override
    public void addAgency(Agency agency) {
        agencyMapper.insertAgency(agency);
    }

    private void setImg(List<User> userList) {
        userList.forEach(i -> {
            i.setAvatar(imgPrefix + i.getAvatar());
        });
    }
}
