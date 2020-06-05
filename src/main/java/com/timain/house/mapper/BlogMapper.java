package com.timain.house.mapper;

import com.timain.house.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 13:17
 */
@Mapper
public interface BlogMapper {

    /**
     * 分页查询博客列表
     * @param params
     * @return
     */
    List<Blog> findBlog(Map<String, Object> params);

    /**
     * 查询博客总数
     * @param blog
     * @return
     */
    Long selectBlogCount(Blog blog);
}
