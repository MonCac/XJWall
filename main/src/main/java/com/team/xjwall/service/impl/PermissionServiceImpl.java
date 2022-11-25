package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Permission;
import com.team.xjwall.mapper.PermissionMapper;
import com.team.xjwall.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public int addPermission(int permissionId, String permissionName, int operationId) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_id",permissionId);
        Permission permission = baseMapper.selectOne(wrapper);
        if (permission != null) {
            return 0;
        }else {
            Permission newPermission = new Permission(permissionId, permissionName, operationId);
            return baseMapper.insert(newPermission);
        }
    }

    @Override
    public int delPermission(String permissionName) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_name",permissionName);
        return baseMapper.delete(wrapper);
    }

    @Override
    public int updatePermission(int permissionId, String permissionName) {
        return 0;
    }

    @Override
    public Permission findOneByName(String permissionName) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_name",permissionName);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public RestResult findPermissionPage(int current, int limit, Permission permission) {
        Page<Permission> page = new Page<>(current, limit);
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(permission.getPermissionId())){
            wrapper.like("permission_id",permission.getPermissionId());
        }else if(!StringUtils.isEmpty(permission.getPermissionName())){
            wrapper.like("permission_name",permission.getPermissionName());
        }
        baseMapper.selectPage(page,wrapper);
        List<Permission> list = page.getRecords();
        long total = page.getTotal();
        long pages = page.getPages();
        return RestResult.ok().data("rows",list).data("total",total).
                data("pages",pages).data("current", current).data("limit", limit);
    }
}
