package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.User;
import com.team.xjwall.mapper.UserMapper;
import com.team.xjwall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User loginUser(User user, String password) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",user.getUserName());
        wrapper.eq("password",user.getPassword());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public int delUser(String userName) {
        return 0;
    }



    @Override
    public int findUserIdByUserName(String userName) {
        QueryWrapper<User> wrapper =new QueryWrapper<>();
        wrapper.eq("user_id", userName);
        return baseMapper.selectOne(wrapper).getUserId();
    }

    @Override
    public User findByUserName(String userName) {
        QueryWrapper<User> wrapper =new QueryWrapper<>();
        wrapper.eq("user_id", userName);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public RestResult findSensitivePage(int current, int limit, User user) {
        Page<User> page = new Page<>(current,limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(user.getUserId())){
            wrapper.like("word_id",user.getUserId());
        }else if (!StringUtils.isEmpty(user.getUserName())){
            wrapper.like("word",user.getUserName());
        }
        baseMapper.selectPage(page,wrapper);
        List<User> list = page.getRecords();
        long total = page.getTotal();
        long pages = page.getPages();
        return RestResult.ok().data("rows",list).data("total",total).
                data("pages",pages).data("current", current).data("limit", limit);
    }
}