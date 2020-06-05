package com.timain.house.page;

import lombok.Data;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:59
 */
@Data
public class PageParams {

    private static final Integer PAGE_SIZE = 5;

    private Integer pageSize;
    private Integer pageNum;
    private Integer offset;
    private Integer limit;

    public PageParams() {
        this(PAGE_SIZE, 1);
    }

    public PageParams(Integer pageSize, Integer pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.offset = pageSize * (pageNum - 1);
        this.limit = pageSize;
    }

    public static PageParams build(Integer pageNum, Integer pageSize) {
        if (null==pageNum) {
            pageNum = 1;
        }
        if (null==pageSize) {
            pageSize = PAGE_SIZE;
        }
        return new PageParams(pageNum, pageSize);
    }
}
