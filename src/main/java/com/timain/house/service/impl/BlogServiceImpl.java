package com.timain.house.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.timain.house.mapper.BlogMapper;
import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Blog;
import com.timain.house.service.BlogService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 13:29
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    /**
     * 分页查询博客信息
     *
     * @param blog
     * @param pageParams
     * @return
     */
    @Override
    public PageData<Blog> selectBlog(Blog blog, PageParams pageParams) {
        Map<String, Object> map = new HashMap<>();
        map.put("blog", blog);
        map.put("pageParams", pageParams);
        List<Blog> blogList = blogMapper.findBlog(map);
        Long count = blogMapper.selectBlogCount(blog);
        populate(blogList);
        return PageData.buildPage(pageParams.getPageNum(), pageParams.getPageSize(), count, blogList);
    }

    private void populate(List<Blog> blogList) {
        if (!blogList.isEmpty()) {
            blogList.stream().forEach(item -> {
                String stripped = Jsoup.parse(item.getContent()).text();
                item.setDigest(stripped.substring(0, Math.min(stripped.length(), 40)));
                String tags = item.getTags();
                item.getTagList().addAll(Lists.newArrayList(Splitter.on(",").split(tags)));
            });
        }
    }

    /**
     * 根据id查询博客信息
     *
     * @param id
     * @return
     */
    @Override
    public Blog findById(int id) {
        Blog blog = new Blog();
        blog.setId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("blog",blog);
        map.put("params",new PageParams(1,1));
        List<Blog> list = blogMapper.findBlog(map);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
