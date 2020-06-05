package com.timain.house.controller;

import com.timain.house.constants.CommonConstants;
import com.timain.house.page.PageData;
import com.timain.house.page.PageParams;
import com.timain.house.pojo.Blog;
import com.timain.house.pojo.Comment;
import com.timain.house.pojo.House;
import com.timain.house.service.BlogService;
import com.timain.house.service.CommentService;
import com.timain.house.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 13:59
 */
@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RecommendService recommendService;

    /**
     * 分页查询博客列表
     * @param blog
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/blog/list")
    public String blogList(Blog blog, Integer pageNum, Integer pageSize, ModelMap modelMap) {
        PageData<Blog> blogPageData = blogService.selectBlog(blog, PageParams.build(pageNum, pageSize));
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("ps", blogPageData);
        return "/blog/listing";
    }

    /**
     * 查询博客详情
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("/blog/detail")
    public String blogDetail(int id, ModelMap modelMap) {
        Blog blog = blogService.findById(id);
        List<Comment> blogComments = commentService.getBlogComments(id, 8);
        List<House> hotHouse = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
        modelMap.put("recomHouses", hotHouse);
        modelMap.put("blog", blog);
        modelMap.put("commentList", blogComments);
        return "/blog/detail";
    }
}
