package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Role;
import com.team.xjwall.mapper.RoleMapper;
import com.team.xjwall.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public int addRole(int roleId, String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        Role role = baseMapper.selectOne(wrapper);
        if (role!=null){
            return 0;
        }else {
            Role newRole = new Role(roleId, roleName);
            return baseMapper.insert(newRole);
        }
    }

    @Override
    public int delRole(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name",roleName);
        return baseMapper.delete(wrapper);
    }

    @Override
    public int updateRole(int roleId, String roleName) {
        return 0;
    }

    @Override
    public Role findOneByName(String roleName) {
        return null;
    }

    @Override
    public RestResult findRolePage(int current, int limit, Role role) {
        return null;
    }
}
