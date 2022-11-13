package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.xjwall.model.Post;
import com.team.xjwall.mapper.PostMapper;
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
                wrapper.orderByDesc("creat_time");
                break;
            //时间升序
            case 2:
                wrapper.orderByAsc("creat_time");
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
        if(type.equals("post_id")){wrapper.eq("post_id",Integer.parseInt(value));}
        else if(type.equals("owner_id")){wrapper.eq("owner_id",Integer.parseInt(value));}
        else if(type.equals("father_post")){wrapper.eq("father_post",Integer.parseInt(value));}
        else if(type.equals("type_id")){wrapper.eq("type_id",Integer.parseInt(value));}
        else{wrapper.eq(type,value);}
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<Integer, List<Post>> findCommentByPostid(int postid) {
        Map<Integer, List<Post>> map=new HashMap<Integer, List<Post>>();
        CommentLoop(postid,0,map);
        return map;
    }
    //循环获取评论的评论的......
    public void CommentLoop(int postid,int index,Map<Integer, List<Post>> map){
        List<Post> temp;
        if(index==0)temp=findByxxx("father_post",""+postid,5);
        else temp=findByxxx("father_post",""+postid,2);
        if(!temp.isEmpty())return;
        else map.put(postid,temp);
        for(Post post:temp)
            CommentLoop(post.getPostId(),1,map);

    }

}
