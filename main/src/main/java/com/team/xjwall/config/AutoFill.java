package com.team.xjwall.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AutoFill implements MetaObjectHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoFill.class);

    @Override
    public void insertFill(MetaObject metaObject) {  // 添加的自动填充
        // createTime、updateTime实体类中属性名
        this.setFieldValByName("createTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {  // 修改的自动填充
    }

}


