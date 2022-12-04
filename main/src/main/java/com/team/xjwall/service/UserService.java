package com.team.xjwall.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Post;
import com.team.xjwall.model.SensitiveS;
import com.team.xjwall.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param user 登录的用户
     * @param password 用户密码
     * @return 用户
     */
    User loginUser(User user, String password);

    /**
     * 删除用户
     * @param userName  用户账号
     * @return  删除成功与否
     */
    int delUser(String userName);


    /**
     * 通过用户账号查找用户id
     * @param userName 用户账号
     * @return 用户id
     */
    int findUserIdByUserName(String userName);


    /**
     * 通过用户账号查找用户
     * @param userName 用户账号
     * @return 用户
     */
    User findByUserName(String userName);

    /**
     * 对用户实现分页查询
     * @param current 当前页
     * @param limit 总页
     * @param user 作为查找条件
     */


    RestResult findUserPage(int current, int limit, User user);

    List<Post> showPosts(User user);

    RestResult update(User user);
}
