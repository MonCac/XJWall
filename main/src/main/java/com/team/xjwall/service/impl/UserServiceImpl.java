package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team.xjwall.model.User;
import com.team.xjwall.mapper.UserMapper;
import com.team.xjwall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        wrapper.eq("empname",user.getUserName());
        wrapper.eq("password",user.getPassword());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public int delUser(String userName) {
        return 0;
    }



    @Override
    public int findUserIdByUserName(String userName) {
        return 0;
    }

    @Override
    public User findByUserName(String userName) {
        return null;
    }


}
