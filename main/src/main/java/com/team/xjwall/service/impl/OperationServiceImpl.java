package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Operation;
import com.team.xjwall.mapper.OperationMapper;
import com.team.xjwall.service.OperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 操作表 服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {

    @Override
    public int addOperation(int operationId, String operationName) {
        QueryWrapper<Operation> wrapper = new QueryWrapper<>();
        wrapper.eq("operation_id",operationId);
        Operation operation = baseMapper.selectOne(wrapper);
        if (operation != null) {
            return 0;
        }else {
            Operation newOperation = new Operation(operationId, operationName);
            return baseMapper.insert(newOperation);
        }
    }

    @Override
    public int delOperation(String operationName) {
        QueryWrapper<Operation> wrapper = new QueryWrapper<>();
        wrapper.eq("operation_name",operationName);
        return baseMapper.delete(wrapper);
    }

    @Override
    public int updateOperation(int operationId, String operationName) {
        return 0;
    }

    @Override
    public Operation findOneByName(String operationName) {
        QueryWrapper<Operation> wrapper = new QueryWrapper<>();
        wrapper.eq("operation_name",operationName);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public RestResult findOperationPage(int current, int limit, Operation operation) {
        Page<Operation> page = new Page<>(current, limit);
        QueryWrapper<Operation> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(operation.getOperationId())){
            wrapper.like("operation_id",operation.getOperationId());
        } else if (!StringUtils.isEmpty(operation.getOperationName())) {
            wrapper.like("operation_name",operation.getOperationName());
        }
        baseMapper.selectPage(page,wrapper);
        List<Operation> list = page.getRecords();
        long total = page.getTotal();
        long pages = page.getPages();
        return RestResult.ok().data("rows",list).data("total",total).
                data("pages",pages).data("current", current).data("limit", limit);
    }
}
