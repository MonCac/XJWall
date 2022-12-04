package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Post;
import com.team.xjwall.model.User;
import com.team.xjwall.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static String thisUserName;
    String regex = "^[a-z0-9A-Z]+$";

    @Autowired
    private UserService userservice;

    @ApiOperation(value = "用户的注册")
    @PostMapping("/add")
    public RestResult add(@RequestBody User user) {
        User flag = userservice.findByUserName(user.getUserName());
        if (flag == null) {
            // 数据库不存在该用户名
            userservice.save(user);
            if (!user.getPassword().matches(regex)) {
                return RestResult.error().message("用户名只能包含字母和数字，请重新填写！");
            }
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }
    @ApiOperation(value = "用户的登录")
    @PostMapping("/login")
    public RestResult login(@RequestBody User user, String password) {
        User login = userservice.loginUser(user, password);
        if (login != null) {
            thisUserName=user.getUserName();
            return RestResult.ok().data("登录成功", login);
        }
        else {
            return RestResult.error();
        }
    }
    @ApiOperation(value = "用户的删除")
    @DeleteMapping("{userId}")
    public RestResult delete(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @PathVariable int userId) {
        boolean flag = userservice.removeById(userId);
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "用户信息的修改")
    @PostMapping("/update")
    public RestResult update(@RequestBody User user) {
        boolean flag = userservice.updateById(user);
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    //用户查找，初步的想法是做一个可视化的搜索结果，然后点击头像可以访问主页
    @ApiOperation("用户的查找") //返回的是User类型，还没有想好应该怎么处理
    @GetMapping("/find/{username}")
    public RestResult getByName(
            @ApiParam(name = "username",value = "用户账号",required = true)
            @PathVariable String username){
        User user = userservice.findByUserName(username);
        if (user!=null){
            return RestResult.ok().data("user",user);
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation("查找用户ID")
    @GetMapping("/findId/{username}")
    public RestResult getIdByName(
            @ApiParam(name = "username",value = "用户账号",required = true)
            @PathVariable String username){
        int user = userservice.findUserIdByUserName(username);
        return RestResult.ok().data("user",user);
    }

}



