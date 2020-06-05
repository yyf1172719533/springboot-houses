package com.timain.house.service.impl;

import com.google.common.collect.Lists;
import com.timain.house.pojo.City;
import com.timain.house.service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 10:56
 */
@Service
public class CityServiceImpl implements CityService {

    @Override
    public List<City> getAllCitys() {
        City city = new City();
        city.setCityName("北京");
        city.setCityCode("110000");
        city.setId(1);
        return Lists.newArrayList(city);
    }
}
