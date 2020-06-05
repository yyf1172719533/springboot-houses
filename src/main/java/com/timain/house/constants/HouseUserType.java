package com.timain.house.constants;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:20
 */
public enum HouseUserType {

    SALE(1),BOOKMARK(2);

    public final Integer value;

    private HouseUserType(Integer value) {
        this.value = value;
    }
}
