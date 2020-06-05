package com.timain.house.pojo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:07
 */
@Data
public class Blog {

    private Integer id;
    private String tags;//标签
    private String content;//内容
    private String title;
    private Date createTime;
    private String digest;

    private List<String> tagList = Lists.newArrayList();
}
