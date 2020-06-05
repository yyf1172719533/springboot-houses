package com.timain.house.service;

import com.timain.house.pojo.House;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/2 11:37
 */
public interface RecommendService {

    void increase(Long id);

    List<Long> getHot();

    List<House> getHotHouse(Integer size);

    List<House> getLast();
}
