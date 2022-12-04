package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team.xjwall.model.Post;
import com.team.xjwall.mapper.PostMapper;
import com.team.xjwall.model.UserPost;
import com.team.xjwall.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Override
    public Post findByTitle(String title){
        QueryWrapper<Post> wrapper =new QueryWrapper<>();
        wrapper.eq("title",title);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<Post> findByxxx(String type, String value, int sortid) {
        QueryWrapper<Post> wrapper =new QueryWrapper<>();
        switch(sortid){
            //时间降序
            case 1:
                wrapper.orderByDesc("create_time");
                break;
            //时间升序
            case 2:
                wrapper.orderByAsc("create_time");
                break;
            //帖子id降序
            case 3:
                wrapper.orderByDesc("post_id");
                break;
            //帖子id升序
            case 4:
                wrapper.orderByAsc("post_id");
                break;
            //点赞降序
            case 5:
                wrapper.orderByDesc("likes");
                break;
            //收藏降序
            case 6:
                wrapper.orderByDesc("collections");
                break;
            //观看量降序
            case 7:
                wrapper.orderByDesc("views");
                break;
            default:
                break;
        }
        if("keyword".equals(type)){
            //简单的模糊搜索
            wrapper.like("content","%"+value).or().like("content",value+"%").or().like("content","%"+value+"%").or().like("title","%"+value).or().like("title",value+"%").or().like("content","%"+value+"%");
        }
        else if("post_id".equals(type)){
            wrapper.eq("post_id",Integer.parseInt(value));
        }
        else if("owner_id".equals(type)){
            wrapper.eq("owner_id",Integer.parseInt(value));
        }
        else if("father_post".equals(type)){
            wrapper.eq("father_post",Integer.parseInt(value));
        }
        else if("type_id".equals(type)){
            wrapper.eq("type_id",Integer.parseInt(value));
        }
        else{
            wrapper.eq(type,value);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<Integer, List<Post>> findCommentByPostid(int postid) {
        Map<Integer, List<Post>> map= new HashMap<>();
        commentLoop(postid,0,map);
        return map;
    }

    @Override
    public void updateLikes(int postid,boolean flag) {
        QueryWrapper<Post> wrapper=new QueryWrapper<>();
        wrapper.eq("post_id",postid);
        Post post=baseMapper.selectOne(wrapper);
        int likes=post.getLikes();
        if(flag) {
            likes+=1;
        } else {
            likes-=1;
        }
        UpdateWrapper<Post> wrapper1=new UpdateWrapper<>();
        wrapper1.eq("post_id",postid).set("likes",likes);
        baseMapper.update(new Post(),wrapper1);

    }

    @Override
    public void updateCollections(int postid, boolean flag) {
        QueryWrapper<Post> wrapper=new QueryWrapper<>();
        wrapper.eq("post_id",postid);
        Post post=baseMapper.selectOne(wrapper);
        int collections=post.getCollections();
        if(flag) {
            collections+=1;
        } else {
            collections-=1;
        }
        UpdateWrapper<Post> wrapper1=new UpdateWrapper<>();
        wrapper1.eq("post_id",postid).set("collections",collections);
        baseMapper.update(new Post(),wrapper1);
    }

    @Override
    public void updateViews(int postid) {
        QueryWrapper<Post> wrapper=new QueryWrapper<>();
        wrapper.eq("post_id",postid);
        Post post=baseMapper.selectOne(wrapper);
        int views=post.getViews();
        views+=1;
        UpdateWrapper<Post> wrapper1=new UpdateWrapper<>();
        wrapper1.eq("post_id",postid).set("views",views);
        baseMapper.update(new Post(),wrapper1);
    }


    /**
     * 循环获取评论的评论的......
     * @param postid 帖子的id
     * @param index （排序的方式？）
     * @param map 键为postid；值为帖子列表
     */
    public void commentLoop(int postid, int index, Map<Integer, List<Post>> map){
        List<Post> temp;
        if(index==0) {
            temp=findByxxx("father_post",""+postid,5);
        } else {
            temp=findByxxx("father_post",""+postid,2);
        }
        if(temp.isEmpty()) {
            return;
        } else {
            map.put(postid,temp);
        }
        for(Post post:temp) {
            commentLoop(post.getPostId(),1,map);
        }

    }

    //获取指定字段(点赞、收藏、浏览)的值
    public int getValue(int postid,String s){
        QueryWrapper<Post> wrapper=new QueryWrapper<>();
        wrapper.eq("post_id",postid);
        Post post=baseMapper.selectOne(wrapper);
        if(s.equals("likes")) {
            return post.getLikes();
        }
        if(s.equals("collections")) {
            return post.getCollections();
        }
        if(s.equals("views")) {
            return post.getViews();
        }
        return -1;
    }
}
