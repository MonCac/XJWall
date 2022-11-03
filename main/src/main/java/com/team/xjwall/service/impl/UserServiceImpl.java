package com.team.xjwall.service.impl;

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

}
