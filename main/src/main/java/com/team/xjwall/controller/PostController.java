package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Post;
import com.team.xjwall.model.UserPost;
import com.team.xjwall.service.PostService;
import com.team.xjwall.service.TypeService;
import com.team.xjwall.service.UserPostService;
import com.team.xjwall.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService ps;
    @Autowired
    private TypeService ts;
    @Autowired
    private UserService us;
    @Autowired
    private UserPostService ups;

    @ApiOperation(value = "帖子的增加")
    @PostMapping("/add")
    public RestResult add(@RequestBody Post post) {
        Post flag = ps.findByTitle(post.getTitle());
        if (flag == null) {
            ps.save(post);
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "通过帖子id查询帖子")
    @GetMapping("/getByPostid/{postid}/{sortid}")
    public RestResult getByPostid(@ApiParam(name = "postid", value = "帖子id", required = true)
                                      @PathVariable int postid,
                                  @ApiParam(name = "sortid", value = "排序id", required = true)
                                      @PathVariable int sortid) {
        List<Post> postList = ps.findByxxx("post_id", "" + postid, sortid);
        if (!postList.isEmpty()) {
            return RestResult.ok().data("post", postList.get(0));
        }
        return RestResult.error();
    }

    @ApiOperation(value = "通过帖子类型查询帖子")
    @GetMapping("/getByPosttype/{typename}/{sortid}")
    public RestResult getByPostid(@ApiParam(name = "typename", value = "类型名", required = true)
                                  @PathVariable String typename,
                                  @ApiParam(name = "sortid", value = "排序id", required = true)
                                  @PathVariable int sortid) {
        int typeid = ts.findByTypeName(typename);
        List<Post> postList = ps.findByxxx("type_id", "" + typeid, sortid);
        if (!postList.isEmpty()) {
            return RestResult.ok().data("post", postList);
        }
        return RestResult.error();
    }

    @ApiOperation(value = "通过发帖人用户名查询帖子")
    @GetMapping("/getByOwnername/{username}/{sortid}")
    public RestResult getByOwnername(@ApiParam(name = "username", value = "用户名", required = true)
                                         @PathVariable String username,
                                     @ApiParam(name = "sortid", value = "排序id", required = true)
                                         @PathVariable int sortid) {
        int ownerid = us.findUserIdByUserName(username);
        List<Post> postList = ps.findByxxx("owner_id", "" + ownerid, sortid);
        if (!postList.isEmpty()) {
            return RestResult.ok().data("post", postList);
        }
        return RestResult.error();
    }

    @ApiOperation(value = "通过发帖人id查询帖子")
    @GetMapping("/getByOwnerid/{userid}/{sortid}")
    public RestResult getByOwnerid(@ApiParam(name = "userid", value = "用户id", required = true)
                                       @PathVariable int userid,
                                   @ApiParam(name = "sortid", value = "排序id", required = true)
                                       @PathVariable int sortid) {
        List<Post> postList = ps.findByxxx("owner_id", "" + userid, sortid);
        if (!postList.isEmpty()) {
            return RestResult.ok().data("post", postList);
        }
        return RestResult.error();
    }

    @ApiOperation(value = "通过帖子名查询帖子")
    @GetMapping("/getBytitle/{title}/{sortid}")
    public RestResult getByTitle(@ApiParam(name = "title", value = "帖子标题", required = true)
                                     @PathVariable String title,
                                 @ApiParam(name = "sortid", value = "排序id", required = true)
                                     @PathVariable int sortid) {
        List<Post> postList = ps.findByxxx("title", title, sortid);
        if (!postList.isEmpty()) {
            return RestResult.ok().data("post", postList);
        }
        return RestResult.error();
    }

    @ApiOperation(value = "查询帖子评论")
    @GetMapping("/getCommentByPostid/{postid}")
    public RestResult getCommentByPostid(@ApiParam(name = "postid", value = "帖子id", required = true)
                                             @PathVariable int postid) {
        Map<Integer, List<Post>> map = ps.findCommentByPostid(postid);
        if (!map.isEmpty()) {
            return RestResult.ok().data("comment", map);
        }
        return RestResult.error();
    }

    @ApiOperation(value = "对帖子进行评论")
    @PostMapping("/addComment")
    public RestResult addComment(@RequestBody Post post) {
        boolean flag=ps.save(post);
        if(flag) return RestResult.ok();
        return RestResult.error();
    }

    @ApiOperation(value = "删除帖子")
    @DeleteMapping("/delete/{postid}")
    public RestResult delete(@ApiParam(name = "postid", value = "帖子id", required = true)
    @PathVariable int postid) {
        List<UserPost> upList=ups.findByPostId(postid);
        List<Integer> idList=new ArrayList<>();
        boolean flag1=true;

        if(!upList.isEmpty()){
            for(UserPost userPost:upList) {
                idList.add(userPost.getId());
            }
            flag1=ups.removeByIds(idList);
        }
        boolean flag2=ps.removeById(postid);
        if(flag1&&flag2) return RestResult.ok();
        return RestResult.error();
    }

    @ApiOperation(value = "点赞帖子")
    @PostMapping("/like/{postid}/{userid}")
    public RestResult like(@ApiParam(name = "postid", value = "帖子id", required = true)
    @PathVariable int postid,@ApiParam(name = "userid", value = "用户id", required = true)
    @PathVariable int userid){
        boolean flag=ups.liked(postid,userid);
        ps.updateLikes(postid,flag);
        if(flag)return RestResult.ok().message("已点赞");
        return RestResult.ok().message("已取消点赞");
    }

    @ApiOperation(value = "收藏帖子")
    @PostMapping("/collection/{postid}/{userid}")
    public RestResult collection(@ApiParam(name = "postid", value = "帖子id", required = true)
                           @PathVariable int postid,@ApiParam(name = "userid", value = "用户id", required = true)
                           @PathVariable int userid){
        boolean flag=ups.collected(postid,userid);
        ps.updateCollections(postid,flag);
        if(flag)return RestResult.ok().message("已收藏");
        return RestResult.ok().message("已取消收藏");
    }

    @ApiOperation(value = "举报帖子")
    @PostMapping("/report/{postid}/{userid}")
    public RestResult repport(@ApiParam(name = "postid", value = "帖子id", required = true)
                                 @PathVariable int postid,@ApiParam(name = "userid", value = "用户id", required = true)
                                 @PathVariable int userid){
        boolean flag=ups.reported(postid,userid);
        if(flag)return RestResult.ok().message("已举报");
        return RestResult.ok().message("请等待管理员对您上次举报请求的处理");
    }

    @ApiOperation(value = "查询被举报帖子")
    @GetMapping("/getReport")
    public RestResult repport(){
        List<UserPost> upList=ups.findReported();
        List<Post> postList=new ArrayList<>();
        if (!upList.isEmpty()) {
            for(UserPost userPost:upList) {
                postList.add(ps.getById(userPost.getPostId()));
            }
            return RestResult.ok().data("userpost", upList).data("post",postList);
        }

        return RestResult.error();
    }

    @ApiOperation(value = "查询已收藏帖子")
    @GetMapping("/getCollection/{userid}")
    public RestResult getCollection(@ApiParam(name = "userid", value = "用户id", required = true)
                                        @PathVariable int userid){
        List<UserPost> upList=ups.findCollected(userid);
        List<Post> postList=new ArrayList<>();
        if (!upList.isEmpty()) {
            for(UserPost userPost:upList) {
                postList.add(ps.getById(userPost.getPostId()));
            }
            return RestResult.ok().data("userpost", upList).data("post",postList);
        }
        return RestResult.error();
    }
 
    @ApiOperation(value = "用户浏览帖子")
    @PostMapping("/view/{postid}/{userid}")
    public RestResult getCollection(@ApiParam(name = "postid", value = "帖子id", required = true)
                                    @PathVariable int postid,@ApiParam(name = "userid", value = "用户id", required = true)
                                    @PathVariable int userid){
        boolean flag=ups.viewed(postid,userid);
        if(flag) ps.updateViews(postid);
        return RestResult.ok();
    }

    @ApiOperation(value = "帖子内容模糊搜索")
    @GetMapping("/search/{keyword}/{sortid}")
    public RestResult getCollection(@ApiParam(name = "keyword", value = "搜索关键词", required = true)
                                    @PathVariable String keyword,@ApiParam(name = "sortid", value = "排序id", required = true)
                                    @PathVariable int sortid){
        List<Post> postList = ps.findByxxx("keyword", keyword, sortid);
        if (!postList.isEmpty()) {
            return RestResult.ok().data("post", postList);
        }
        return RestResult.error();
    }
}

