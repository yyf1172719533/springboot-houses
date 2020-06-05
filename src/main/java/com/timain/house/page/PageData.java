package com.timain.house.page;

import lombok.Data;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2019/12/31 15:53
 */
@Data
public class PageData<T> {

    private List<T> list;
    private Pagination pagination;

    public PageData(Pagination pagination, List<T> list) {
        this.list = list;
        this.pagination = pagination;
    }

    public static <T>PageData<T> buildPage(Integer pageNum, Integer pageSize, Long count, List<T> list) {
        Pagination pagination = new Pagination(pageNum, pageSize, count);
        return new PageData<>(pagination, list);
    }
}
