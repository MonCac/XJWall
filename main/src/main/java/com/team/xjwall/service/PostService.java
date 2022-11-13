package com.team.xjwall.service;

import com.team.xjwall.model.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface PostService extends IService<Post> {
    //根据标题查询
    Post findByTitle(String title);
    //根据属性type查询值为value的帖子，并将结果按sortid排序
    List<Post> findByxxx(String type, String value, int sortid);
    //根据id查询该帖子的所有评论
    //key为父帖ID，value为该父帖ID的评论表
    Map<Integer,List<Post>> findCommentByPostid(int postid);
}
