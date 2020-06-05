package com.timain.house.pojo;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 14:20
 */
@Data
public class House {

    private Long id;
    private String name;//房产名称
    private Integer type;
    private Integer price;
    private String images;//图片地址
    private Integer area;//面积
    private Integer beds;//卧室数量
    private Integer baths;//卫生间数量
    private double rating;//评级
    private Integer roundRating = 0;
    private String remarks;//房产描述
    private String properties;//属性
    private String floorPlan;//户型图
    private String tags;//标签
    private Date createTime;
    private Integer cityId;
    private Integer communityId;
    private String address;
    private Integer state;
    private String firstImg;
    private List<String> imageList = Lists.newArrayList();
    private List<String> floorPlanList = Lists.newArrayList();
    private List<String> featureList = Lists.newArrayList();
    private List<MultipartFile> houseFiles;
    private List<MultipartFile> floorPlanFiles;
    private String priceStr;
    private String typeStr;
    private Long userId;
    private boolean bookmarked;
    private List<Long> ids;
    private String sort = "time_desc";

    public void setType(Integer type) {
        this.type = type;
        if (type.equals(1)) {
            this.typeStr = "For Sale";
        }else {
            this.typeStr = "For Rent";
        }
    }

    public void setPrice(Integer price) {
        this.price = price;
        this.priceStr = this.price + "万";
    }

    public void setProperties(String properties) {
        this.properties = properties;
        this.featureList = Splitter.on(",").splitToList(properties);
    }

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
        if (!Strings.isNullOrEmpty(floorPlan)) {
            this.floorPlanList = Splitter.on(",").splitToList(floorPlan);
        }
    }

    public void setImages(String images) {
        this.images = images;
        if (!Strings.isNullOrEmpty(images)) {
            List<String> list = Splitter.on(",").splitToList(images);
            if (list.size() > 0) {
                this.firstImg = list.get(0);
                this.imageList = list;
            }
        }
    }
}
