package com.timain.house.pojo;

import lombok.Data;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:02
 */
@Data
public class Community {

    private Integer id;
    private String cityCode;//城市编码
    private String cityName;//城市名称
    private String name;//小区名称
}
