package com.team.xjwall.service;

import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Type;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子类型表 服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface TypeService extends IService<Type> {
    /**
     * 对帖子类型的添加——操作者：管理员
     * @param typeName 类型的名称
     * @param typeId   （非必须）类型的id--->那调用这个方法时又必须提供typeId，怎么解决？
     * @return 增加成功与否
     */
    int addType(int typeId,String typeName);

    /**
     * 对帖子类型的删除——操作者：管理员
     * @param typeName  类型的名称
     * @return  删除成功与否
     */
    int delType(String typeName);

    /**
     * 对帖子类型的修改——操作者：管理员
     * @param typeName  类型的名称
     * @param typeId    类型的id
     * @return  修改后的帖子类型
     */
    int updateType(int typeId,String typeName);

    /**
     * 通过类型的名字查找帖子类型——操作者：用户、管理员
     * 用户：发布帖子时需指定类型、通过帖子类型查找帖子id
     * 管理员：通过帖子类型查找帖子id
     * @param typeName  类型的名称,也可以是一部分关键字。
     * @return  返回查询到的类型id
     */
    int findByTypeName(String typeName);

    /**
     * 通过类型名称查找到对应的类型元组
     * @param typeName 类型的名称
     * @return 返回Type实体对象
     */
    Type findOneByTypeName(String typeName);

    /***
     * 分页查询帖子类型
     * @param current 当前页
     * @param limit 总页
     * @param type 作为查找条件
     * @return 查找到的帖子类型
     */
    RestResult findTypePage(int current,int limit,Type type);
}
