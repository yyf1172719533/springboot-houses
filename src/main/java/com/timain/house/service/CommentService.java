package com.timain.house.service;

import com.timain.house.pojo.Comment;

import java.util.List;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 16:27
 */
public interface CommentService {

    /**
     * 查询房产评论信息
     * @param id
     * @return
     */
    List<Comment> getHouseComments(Long id);

    /**
     * 添加房产评论信息
     * @param comment
     * @return
     */
    void addComment(Comment comment);

    /**
     * 查询博客评论列表信息
     * @param blogId
     * @param size
     * @return
     */
    List<Comment> getBlogComments(long blogId, int size);
}
