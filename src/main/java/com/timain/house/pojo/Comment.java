package com.timain.house.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:12
 */
@Data
public class Comment {

    private Long id;
    private String content;//评论内容
    private Long houseId;//房屋id
    private Date createTime;
    private Integer blogId;//博客id
    private Integer type;//类型  1-房产评论  2-博客评论
    private Long userId;
    private String userName;
    private String avatar;
}
