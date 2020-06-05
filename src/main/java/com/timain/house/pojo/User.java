package com.timain.house.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 用户实体类
 * @author yyf
 * @version 1.0
 * @date 2019/12/27 21:20
 */
@Data
public class User {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String aboutMe;//自我介绍
    private String passWd;
    private String confirmPassWd;//确认密码
    private String newPassword;//新密码
    private String avatar;//头像
    private MultipartFile avatarFile;//头像文件
    private String key;//激活链接key
    private Integer type;//用户类型
    private Date createTime;
    private Integer enable;//是否启用
    private Long agencyId;//所属经纪机构
    private String agencyName;//所属经纪机构名称
}
