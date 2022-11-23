package com.team.xjwall.service;

import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * 由于在DBMS中，Role、Permission、Operation、RolePermissionRelation之间存在着关系
 * 得看DBMS中外键设置的是否是级联？—不用考虑，先做基础的不考虑其他条件的增删改查。
 * 2022年11月23日17:13:54——》整个系统的角色权限分配工作尚未完成。
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface RoleService extends IService<Role> {
    /**
     * 对角色的添加
     * @param roleId 角色id
     * @param roleName 角色名
     * @return 成功与否
     */
    int addRole(int roleId,String roleName);

    /**
     * 对角色的删除
     * @param roleName 角色名
     * @return 成功与否
     */
    int delRole(String roleName);

    /**
     * 对角色的修改
     * @param roleId 角色id
     * @param roleName 角色名
     * @return 成功与否
     */
    int updateRole(int roleId,String roleName);

    /**
     * 通过角色名查找角色
     * @param roleName 角色名
     * @return 对象角色
     */
    Role findOneByName(String roleName);

    /**
     * 角色的分页查询
     * @param current 当前页
     * @param limit 总页
     * @param role 作为查找条件
     * @return 查找到的角色
     */
    RestResult findRolePage(int current,int limit,Role role);
}
