package com.team.xjwall.service;

import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Post;
import com.team.xjwall.model.SensitiveS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.xjwall.model.SensitiveS;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  敏感词服务类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
public interface SensitiveSService extends IService<SensitiveS> {
    /**
     * 对敏感词的添加
     * @param wordName 敏感词
     * @param wordId    敏感词的id
     * @return 增加成功与否
     */
    int addSensitiveS(String wordName, int wordId);

    /**
     * 对敏感词的删除
     * @param wordName  敏感词的名称
     * @return  删除成功与否
     */
    int delSensitiveS(String wordName);

    /**
     * 对敏感词的修改
     * @param wordName  敏感词的名称
     * @param wordId    敏感词的id
     * @return  修改后的敏感词
     */
    int updateSensitiveS(String wordName, int wordId);

    /**
     * 通过敏感词查找敏感词
     * @param word 敏感词的名称,也可以是一部分关键字
     * @return  返回查询到的敏感词
     */
    SensitiveS findOneByWord(String word);

    /***
     * 分页查询敏感词
     * @param current 当前页
     * @param limit 总页
     * @param sensitiveS 作为查找条件
     * @return 查找到的敏感词
     */
    RestResult findSensitiveSPage(int current, int limit, SensitiveS sensitiveS);

    /**
     * 返回帖子内容的敏感词汇占比——使用场景：发布一个帖子后、管理员对某一帖子的检查
     * @param postContent 帖子的内容
     * @return 比例
     */
    int findSensitiveSProportion(String postContent);

    /**
     * 所有帖子内容的敏感词汇占比——使用场景：不常使用
     * @param posts 所有的帖子
     * @return 键值对，键为帖子id，值为其敏感比例
     */
    Map<Integer,Integer> findAllProportion(List<Post> posts);
}
