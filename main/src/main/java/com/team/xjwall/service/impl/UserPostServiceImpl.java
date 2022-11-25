package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team.xjwall.model.Post;
import com.team.xjwall.model.UserPost;
import com.team.xjwall.mapper.UserPostMapper;
import com.team.xjwall.service.UserPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class UserPostServiceImpl extends ServiceImpl<UserPostMapper, UserPost> implements UserPostService {

    @Override
    public boolean liked(int postid, int userid) {
        UserPost up=existed(postid,userid);
        if(up!=null){
            UpdateWrapper<UserPost> wrapper1=new UpdateWrapper<>();
            int like;
            if(up.getIslike()==null)like=1;
            else like=Math.abs(up.getIslike()-1);
            wrapper1.eq("post_id",postid).eq("user_id",userid).set("islike",like);
            baseMapper.update(null,wrapper1);
            if(like==1)return true;
        }else{
            UserPost up1=new UserPost();
            up1.setPostId(postid);
            up1.setUserId(userid);
            up1.setIslike(1);
//            up1.setIscollected(0);
//            up1.setIsreported(0);
//            up1.setIsviewed(0);
            this.save(up1);
            return true;
        }
        return false;
    }

    @Override
    public boolean collected(int postid, int userid) {
        UserPost up=existed(postid,userid);
        if(up!=null){
            UpdateWrapper<UserPost> wrapper1=new UpdateWrapper<>();
            int collection;
            if(up.getIscollected()==null)collection=1;
            else collection=Math.abs(up.getIscollected()-1);
            wrapper1.eq("post_id",postid).eq("user_id",userid).set("iscollected",collection);
            baseMapper.update(null,wrapper1);
            if(collection==1)return true;
        }else{
            UserPost up1=new UserPost();
            up1.setPostId(postid);
            up1.setUserId(userid);
            up1.setIscollected(1);
            this.save(up1);
            return true;
        }
        return false;
    }

    @Override
    public boolean viewed(int postid, int userid) {
        UserPost up=existed(postid,userid);
        if(up!=null){
            UpdateWrapper<UserPost> wrapper1=new UpdateWrapper<>();
            if(up.getIsviewed()==1)return false;
            wrapper1.eq("post_id",postid).eq("user_id",userid).set("isviewed",1);
            baseMapper.update(null,wrapper1);
        }else{
            UserPost up1=new UserPost();
            up1.setPostId(postid);
            up1.setUserId(userid);
            up1.setIscollected(1);
            this.save(up1);
        }
        return true;
    }

    @Override
    public boolean reported(int postid, int userid) {
        UserPost up=existed(postid,userid);
        if(up!=null){
            UpdateWrapper<UserPost> wrapper1=new UpdateWrapper<>();
            if(up.getIsreported()==1)return false;
            wrapper1.eq("post_id",postid).eq("user_id",userid).set("isreported",1);
            baseMapper.update(null,wrapper1);
            return true;
        }else{
            UserPost up1=new UserPost();
            up1.setPostId(postid);
            up1.setUserId(userid);
            up1.setIsreported(1);
            this.save(up1);
            return true;
        }
    }

    @Override
    public List<UserPost> findReported() {
        QueryWrapper<UserPost> wrapper=new QueryWrapper<>();
        wrapper.eq("isreported",1);
        List<UserPost> uplist=baseMapper.selectList(wrapper);
        return uplist;
    }

    @Override
    public List<UserPost> findCollected() {
        QueryWrapper<UserPost> wrapper=new QueryWrapper<>();
        wrapper.eq("iscollected",1);
        List<UserPost> uplist=baseMapper.selectList(wrapper);
        return uplist;
    }

    @Override
    public List<UserPost> findByPostId(int postid) {
        QueryWrapper<UserPost> wrapper=new QueryWrapper<>();
        wrapper.eq("post_id",postid);
        List<UserPost> uplist=baseMapper.selectList(wrapper);
        return uplist;
    }

    //获取数据库中用户-帖子的该条记录
    public UserPost existed(int postid,int userid){
        QueryWrapper<UserPost> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userid);
        wrapper.eq("post_id",postid);
        UserPost up=baseMapper.selectOne(wrapper);
        return up;
    }

}
