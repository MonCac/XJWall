package com.team.xjwall.service;

import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Sensitive;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.xjwall.model.Type;

import java.util.List;

/**
 * <p>
 *  敏感词服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface SensitiveService extends IService<Sensitive> {
    /**
     * 对敏感词的添加
     * @param wordName 敏感词
     * @param wordId    敏感词的id
     * @return 增加成功与否
     */
    int addSensitive(String wordName, int wordId);

    /**
     * 对敏感词的删除
     * @param wordName  敏感词的名称
     * @return  删除成功与否
     */
    int delSensitive(String wordName);

    /**
     * 对敏感词的修改
     * @param wordName  敏感词的名称
     * @param wordId    敏感词的id
     * @return  修改后的敏感词
     */
    int updateSensitive(String wordName, int wordId);

    /**
     * 通过敏感词查找敏感词
     * @param wordName 敏感词的名称,也可以是一部分关键字
     * @return  返回查询到的敏感词
     */
    List<Sensitive> findByWordName(String wordName);

    /***
     * 分页查询敏感词
     * @param current 当前页
     * @param limit 总页
     * @param sensitive 作为查找条件
     * @return 查找到的敏感词
     */
    RestResult findSensitivePage(int current, int limit, Sensitive sensitive);
}
