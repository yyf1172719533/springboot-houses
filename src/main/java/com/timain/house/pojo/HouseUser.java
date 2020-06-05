package com.timain.house.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:14
 */
@Data
public class HouseUser {

    private Long id;
    private Long houseId;
    private Long userId;
    private Date createTime;
    private Integer type;//类型 1-售卖  2-收藏
}
