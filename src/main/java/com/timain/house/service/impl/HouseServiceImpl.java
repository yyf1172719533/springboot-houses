package com.timain.house.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.timain.house.constants.HouseUserType;
import com.timain.house.mapper.HouseMapper;
import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Community;
import com.timain.house.pojo.House;
import com.timain.house.pojo.HouseUser;
import com.timain.house.pojo.User;
import com.timain.house.service.FileService;
import com.timain.house.service.HouseService;
import com.timain.house.service.HouseUserService;
import com.timain.house.utils.BeanHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 14:16
 */
@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private HouseUserService houseUserService;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * 查询所有房屋列表
     *
     * @param house
     * @param pageParams
     * @return
     */
    @Override
    public PageData<House> findAll(House house, PageParams pageParams) {
        List<House> list = Lists.newArrayList();

        if (!Strings.isNullOrEmpty(house.getName())) {
            Community community = new Community();
            community.setName(house.getName());
            List<Community> communityList = houseMapper.selectCommunity(community);
            if (!communityList.isEmpty()) {
                house.setCommunityId(communityList.get(0).getId());
            }
        }
        list = selectAndSet(house, pageParams);
        Long count = houseMapper.selectPageCount(house);
        return PageData.buildPage(pageParams.getPageNum(), pageParams.getPageSize(), count, list);
    }

    @Override
    public List<House> selectAndSet(House house, PageParams pageParams) {
        Map<String, Object> map = new HashMap<>();
        map.put("house", house);
        map.put("pageParams", pageParams);
        List<House> houseList = houseMapper.selectByPage(map);
        houseList.forEach(house1 -> {
            house1.setFirstImg(imgPrefix + house1.getFirstImg());
            house1.setImageList(house1.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            house1.setFloorPlanList(house1.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
        });
        return houseList;
    }

    /**
     * 查询小区信息
     * @return
     */
    @Override
    public List<Community> findAll() {
        return houseMapper.selectCommunity(new Community());
    }

    /**
     * 添加房产信息并绑定用户
     *
     * @param house
     * @param user
     */
    @Override
    public void addHouse(House house, User user) {
        if (CollectionUtils.isNotEmpty(house.getHouseFiles())) {
            String images = Joiner.on(",").join(fileService.getImgPath(house.getHouseFiles()));
            house.setImages(images);
        }
        if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())) {
            String images = Joiner.on(",").join(fileService.getImgPath(house.getFloorPlanFiles()));
            house.setFloorPlan(images);
        }
        BeanHelper.onInsert(house);
        houseMapper.insertHouse(house);
        House newHouse = new House();
        newHouse.setName(house.getName());
        newHouse.setImages(house.getImages());
        House one = houseMapper.selectOne(newHouse);
        bindUserAndHouse(one.getId(), user.getId(), false);
    }

    /**
     * 根据id查询房产信息
     *
     * @param id
     * @return
     */
    @Override
    public House selectOneHouse(Long id) {
        House house = new House();
        house.setId(id);
        List<House> houseList = selectAndSet(house, PageParams.build(1, 1));
        if (!houseList.isEmpty()) {
            return houseList.get(0);
        }
        return null;
    }

    /**
     * 绑定用户房产信息
     *
     * @param houseId
     * @param userId
     * @param flag
     */
    public void bindUserAndHouse(Long houseId, Long userId, boolean flag) {
        System.out.println(houseId);
        Integer type = flag ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value;
        Map<String, Object> map = new HashMap<>();
        map.put("houseId", houseId);
        map.put("userId", userId);
        map.put("type", type);
        HouseUser houseUser = houseUserService.selectHouseUser(map);
        if (null != houseUser) {
            return;
        }
        HouseUser HU = new HouseUser();
        HU.setHouseId(houseId);
        HU.setUserId(userId);
        HU.setType(type);
        BeanHelper.setDefaultProp(HU, HouseUser.class);
        BeanHelper.onInsert(HU);
        houseUserService.addHU(HU);
    }

    /**
     * 取消绑定用户和房产信息
     * @param id
     * @param userId
     * @param userType
     */
    public void unBindUserAndHouse(Long id, Long userId, HouseUserType userType) {
        if (userType.equals(HouseUserType.SALE)) {
            houseMapper.remCollHouse(id);
            return;
        }
        houseUserService.remHouseUser(id, userId, userType.value);
    }
}
