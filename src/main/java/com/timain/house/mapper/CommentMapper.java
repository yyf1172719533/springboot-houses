package com.timain.house.mapper;

import com.timain.house.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/1/7 15:40
 */
@Mapper
public interface CommentMapper {

    /**
     * 查询评论列表
     * @param params
     * @return
     */
    List<Comment> selectComments(Map<String, Object> params);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    int insertComment(Comment comment);

    /**
     * 查询博客评论
     * @param blogId
     * @param size
     * @return
     */
    List<Comment> selectBlogComments(@Param("blogId")long blogId,@Param("size") int size);
}
