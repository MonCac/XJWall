package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.User;
import com.team.xjwall.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserService service;

    @ApiOperation(value = "用户的增加")
    @PostMapping("/add")
    public RestResult add(@RequestBody User user) {
        User flag = service.findByUserName(user.getUserName());
        if (flag == null) {
            // 数据库不存在该用户名
            service.save(user);
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }
    @ApiOperation(value = "用户的登录")
    @PostMapping("/login")
    public RestResult login(@RequestBody User user, String password) {
        User login = service.loginUser(user, password);
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
        boolean flag = service.removeById(userId);
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "用户信息的修改")
    @PostMapping("/update")
    public RestResult update(@RequestBody User user) {
        boolean flag = service.updateById(user);
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }
}



