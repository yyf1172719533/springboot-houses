package com.timain.house.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.House;
import com.timain.house.service.HouseService;
import com.timain.house.service.RecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/2 11:41
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    private static final String HOT_HOUSE_KEY = "hot_house";

    private Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);

    @Autowired
    private HouseService houseService;

    @Override
    public void increase(Long id) {
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id+"");
            jedis.zremrangeByRank(HOT_HOUSE_KEY, 0, -11);
            jedis.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Long> getHot() {
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
            jedis.close();
            List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
            return ids;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Lists.newArrayList();
        }
    }

    @Override
    public List<House> getHotHouse(Integer size) {
        House house = new House();
        List<Long> list = getHot();
        list = list.subList(0, Math.min(list.size(), size));
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }
        house.setIds(list);
        final List<Long> order = list;
        List<House> houseList = houseService.selectAndSet(house, PageParams.build(1, size));
        Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
            return order.indexOf(hs.getId());
        });
        return houseSort.sortedCopy(houseList);
    }

    @Override
    public List<House> getLast() {
        House house = new House();
        house.setSort("create_time");
        List<House> houseList = houseService.selectAndSet(house, PageParams.build(1, 8));
        return houseList;
    }
}
