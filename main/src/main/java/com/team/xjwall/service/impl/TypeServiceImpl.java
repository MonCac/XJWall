package com.team.xjwall.service.impl;

import com.team.xjwall.model.Type;
import com.team.xjwall.mapper.TypeMapper;
import com.team.xjwall.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
