package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Post;
import com.team.xjwall.service.PostService;
import com.team.xjwall.service.TypeService;
import com.team.xjwall.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/getByPostid/{postid}/{sortid}")
    public RestResult getByPostid(@ApiParam(name = "postid", value = "帖子id", required = true)
        @PathVariable int postid,
        @ApiParam(name = "sortid", value = "排序id", required = true)
        @PathVariable int sortid) {
        List<Post> postList=ps.findByxxx("post_id",""+postid,sortid);
        if (!postList.isEmpty())
            return RestResult.ok().data("post",postList.get(0));
        return RestResult.error();
    }

    @ApiOperation(value = "通过帖子类型查询帖子")
    @PostMapping("/getByPosttype/{typename}/{sortid}")
    public RestResult getByPostid(@ApiParam(name = "typename", value = "类型名", required = true)
                                  @PathVariable String typename,
                                  @ApiParam(name = "sortid", value = "排序id", required = true)
                                  @PathVariable int sortid) {
        int typeid=ts.findByTypeName(typename);
        List<Post> postList=ps.findByxxx("type_id",""+typeid,sortid);
        if (!postList.isEmpty())
            return RestResult.ok().data("post",postList);
        return RestResult.error();
    }

    @ApiOperation(value = "通过发帖人用户名查询帖子")
    @PostMapping("/getByOwnername/{username}/{sortid}")
    public RestResult getByOwnername(@ApiParam(name = "username", value = "用户名", required = true)
                                  @PathVariable String username,
                                  @ApiParam(name = "sortid", value = "排序id", required = true)
                                  @PathVariable int sortid) {
        int ownerid=us.findUserIdByUserName(username);
        List<Post> postList=ps.findByxxx("owner_id",""+ownerid,sortid);
        if (!postList.isEmpty())
            return RestResult.ok().data("post",postList);
        return RestResult.error();
    }

    @ApiOperation(value = "通过发帖人id查询帖子")
    @PostMapping("/getByOwnerid/{userid}/{sortid}")
    public RestResult getByOwnerid(@ApiParam(name = "userid", value = "用户id", required = true)
                                  @PathVariable int userid,
                                  @ApiParam(name = "sortid", value = "排序id", required = true)
                                  @PathVariable int sortid) {
        List<Post> postList=ps.findByxxx("owner_id",""+userid,sortid);
        if (!postList.isEmpty())
            return RestResult.ok().data("post",postList);
        return RestResult.error();
    }

    @ApiOperation(value = "通过帖子名查询帖子")
    @PostMapping("/getBytitle/{title}/{sortid}")
    public RestResult getByTitle(@ApiParam(name = "title", value = "帖子标题", required = true)
                                   @PathVariable String title,
                                   @ApiParam(name = "sortid", value = "排序id", required = true)
                                   @PathVariable int sortid) {
        List<Post> postList=ps.findByxxx("title",title,sortid);
        if (!postList.isEmpty())
            return RestResult.ok().data("post",postList);
        return RestResult.error();
    }

    @ApiOperation(value = "查询帖子评论")
    @PostMapping("/getCommentByPostid/{postid}")
    public RestResult getCommentByPostid(@ApiParam(name = "postid", value = "帖子id", required = true)
                                 @PathVariable int postid) {
        Map<Integer,List<Post>> map=ps.findCommentByPostid(postid);
        if(!map.isEmpty())
            return RestResult.ok().data("comment",map);
        return RestResult.error();
    }
}

