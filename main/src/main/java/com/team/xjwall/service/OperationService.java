package com.team.xjwall.service;

import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Operation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.xjwall.model.Role;

/**
 * <p>
 * 操作表 服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface OperationService extends IService<Operation> {
    /**
     * 对操作的添加
     * @param operationId 操作id
     * @param operationName 操作名
     * @return 成功与否
     */
    int addOperation(int operationId,String operationName);

    /**
     * 对操作的删除
     * @param operationName 操作名
     * @return 成功与否
     */
    int delOperation(String operationName);

    /**
     * 对操作的修改
     * @param operationId 操作id
     * @param operationName 操作名
     * @return 成功与否
     */
    int updateOperation(int operationId,String operationName);

    /**
     * 通过操作名查找操作
     * @param operationName 操作名
     * @return 操作对象
     */
    Operation findOneByName(String operationName);

    /**
     * 对操作的分页查询
     * @param current 当前页
     * @param limit 总页
     * @param operation 作为查找条件
     * @return 查找到的操作
     */
    RestResult findOperationPage(int current, int limit, Operation operation);
}
