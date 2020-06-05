package com.timain.house.controller;

import com.timain.house.constants.CommonConstants;
import com.timain.house.interceptor.UserContext;
import com.timain.house.pojo.Comment;
import com.timain.house.pojo.User;
import com.timain.house.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/8 11:31
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param houseId
     * @param content
     * @param modelMap
     * @return
     */
    @RequestMapping("/comment/leaveComment")
    public String leaveComment(Long houseId, String content, ModelMap modelMap) {
        Comment comment = new Comment();
        comment.setHouseId(houseId);
        comment.setContent(content);
        User user = UserContext.getUser();
        comment.setUserId(user.getId());
        comment.setType(1);
        comment.setBlogId(null);
        commentService.addComment(comment);
        return "redirect:/house/detail?id=" + houseId;
    }

    /**
     * 添加博客评论
     * @param blogId
     * @param content
     * @param modelMap
     * @return
     */
    @RequestMapping("/comment/leaveBlogComment")
    public String leaveBlogComment(Integer blogId, String content, ModelMap modelMap) {
        User user = UserContext.getUser();
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setHouseId(null);
        comment.setType(CommonConstants.COMMENT_BLOG_TYPE);
        commentService.addComment(comment);
        return "redirect:/blog/detail?id=" + blogId;
    }
}
