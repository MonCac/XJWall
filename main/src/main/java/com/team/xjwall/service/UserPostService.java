package com.team.xjwall.service;

import com.team.xjwall.model.UserPost;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface UserPostService extends IService<UserPost> {
    //点赞返回true,取消点赞则返回false
    boolean liked(int postid,int userid);
    //同上
    boolean collected(int postid,int userid);
    //记录用户对帖子的浏览情况
    boolean viewed(int postid,int userid);
    //某一用户对该帖子进行举报
    boolean reported(int postid,int userid);
    //返回被举报的帖子-用户关系
    List<UserPost> findReported();
    //返回被收藏的帖子-用户关系
    List<UserPost> findCollected();
    //通过帖子id查找帖子-用户关系
    List<UserPost> findByPostId(int postid);
}
