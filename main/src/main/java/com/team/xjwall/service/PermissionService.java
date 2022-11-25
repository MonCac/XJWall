package com.team.xjwall.service;

import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Operation;
import com.team.xjwall.model.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.xjwall.model.Role;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 对权限的增加
     * @param permissionId 权限id
     * @param permissionName 权限名
     * @param operationId 操作id
     * @return 成功与否
     */
    int addPermission(int permissionId,String permissionName,int operationId);

    /**
     * 对权限的删除
     * @param permissionName 权限名
     * @return 成功与否
     */
    int delPermission(String permissionName);

    /**
     * 对权限的修改
     * @param permissionId 权限id
     * @param permissionName 权限名
     * @return 成功与否
     */
    int updatePermission(int permissionId,String permissionName);

    /**
     * 通过权限名查找权限
     * @param permissionName 权限名
     * @return 对象权限
     */
    Permission findOneByName(String permissionName);

    /**
     * 权限的分页查询
     * @param current 当前页
     * @param limit 总页
     * @param permission 作为查找条件
     * @return 查找到的权限
     */
    RestResult findPermissionPage(int current, int limit, Permission permission);
}
