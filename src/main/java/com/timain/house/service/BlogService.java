package com.timain.house.service;

import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Blog;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 13:27
 */
public interface BlogService {

    /**
     * 分页查询博客信息
     * @param blog
     * @param pageParams
     * @return
     */
    PageData<Blog> selectBlog(Blog blog, PageParams pageParams);

    /**
     * 根据id查询博客信息
     * @param id
     * @return
     */
    Blog findById(int id);
}
