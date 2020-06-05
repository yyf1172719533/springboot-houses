package com.timain.house.pojo;

import lombok.Data;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:04
 */
@Data
public class Agency {

    private Integer id;
    private String name;//经纪机构名称
    private String address;
    private String phone;
    private String email;
    private String aboutUs;
    private String webSite;//网站
    private String mobile;
}
