package com.timain.house.service.impl;

import com.timain.house.mapper.CommentMapper;
import com.timain.house.pojo.Comment;
import com.timain.house.pojo.User;
import com.timain.house.service.CommentService;
import com.timain.house.service.UserService;
import com.timain.house.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 16:29
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;

    /**
     * 查询房产评论信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Comment> getHouseComments(Long id) {
        Map<String, Object> params = new HashMap<>();
        /*Comment comment = new Comment();*/
        params.put("houseId", id);
        params.put("size", 8);
        List<Comment> comments = commentMapper.selectComments(params);
        comments.forEach(comment -> {
            User user = userService.findById(comment.getUserId());
            comment.setAvatar(user.getAvatar());
            comment.setUserName(user.getName());
        });
        return comments;
    }

    /**
     * 添加房产评论信息
     *
     * @param comment
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addComment(Comment comment) {
        Comment newComment = new Comment();
        if (comment.getType() == 1) {
            newComment.setHouseId(comment.getHouseId());
        } else {
            newComment.setBlogId(comment.getBlogId());
        }
        newComment.setContent(comment.getContent());
        newComment.setUserId(comment.getUserId());
        newComment.setType(comment.getType());
        BeanHelper.onInsert(newComment);
        BeanHelper.setDefaultProp(newComment, Comment.class);
        commentMapper.insertComment(newComment);
    }

    /**
     * 查询博客评论列表信息
     *
     * @param blogId
     * @param size
     * @return
     */
    @Override
    public List<Comment> getBlogComments(long blogId, int size) {
        List<Comment> commentList = commentMapper.selectBlogComments(blogId, size);
        commentList.forEach(comment -> {
            User user = userService.findById(comment.getUserId());
            comment.setUserName(user.getName());
            comment.setAvatar(user.getAvatar());
        });
        return commentList;
    }
}
