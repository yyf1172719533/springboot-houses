package com.timain.house.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:17
 */
@Data
public class UserMsg {

    private Long id;
    private String msg;//消息
    private Long userId;
    private Date createTime;
    private Long agentId;//经纪人id
    private Long houseId;
    private String email;
    private String userName;
}
