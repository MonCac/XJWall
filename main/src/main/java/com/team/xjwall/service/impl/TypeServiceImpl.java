package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Type;
import com.team.xjwall.mapper.TypeMapper;
import com.team.xjwall.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 帖子类型表 服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {


    @Override
    public int addType(int typeId, String typeName) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id",typeId);
        Type type = baseMapper.selectOne(wrapper);
        //id是非必须的，那如果之前已经存在了的id，不进行覆盖。
        if (type!=null){
            return 0;
        }else {
            Type newType = new Type(typeId,typeName);
            return baseMapper.insert(newType);
        }
    }

    @Override
    public int delType(String typeName) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        //选出指定类型名称的帖子类型
        wrapper.eq("type_name",typeName);
        return baseMapper.delete(wrapper);
    }

    @Override
    public int updateType(int typeId, String typeName) {
        Type type = new Type();
        type.setTypeId(typeId);
        type.setTypeName(typeName);
        return baseMapper.updateById(type);
    }

    @Override
    public int findByTypeName(String typeName) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("type_name",typeName);
        return baseMapper.selectOne(wrapper).getTypeId();
    }

    @Override
    public Type findOneByTypeName(String typeName) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("type_name",typeName);
        return baseMapper.selectOne(wrapper);
    }


    @Override
    public RestResult findTypePage(int current, int limit, Type type) {
        //创建分页对象
        Page<Type> page = new Page<>(current,limit);
        QueryWrapper<Type> typeQueryWrapper = new QueryWrapper<>();
        //条件构造器-构造条件
        if (!StringUtils.isEmpty(type.getTypeId())){
            typeQueryWrapper.like("type_id",type.getTypeId());
        }else if (!StringUtils.isEmpty(type.getTypeName())){
            typeQueryWrapper.like("type_name",type.getTypeName());
        }
        //调用分页查询的方法
        baseMapper.selectPage(page,typeQueryWrapper);
        //获取表中的显示的数据
        List<Type> list = page.getRecords();
        //获取表中总记录数
        long total = page.getTotal();
        //获取分页总数
        long pages = page.getPages();
        //将分页信息封装在Map集合中
        return RestResult.ok().data("rows",list).data("total",total).
                data("pages",pages).data("current", current).data("limit", limit);
    }


}
