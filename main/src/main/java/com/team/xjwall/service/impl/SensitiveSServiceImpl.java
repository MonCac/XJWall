package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.mapper.SensitiveSMapper;
import com.team.xjwall.model.Post;
import com.team.xjwall.model.SensitiveS;
import com.team.xjwall.service.SensitiveSService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class SensitiveSServiceImpl extends ServiceImpl<SensitiveSMapper, SensitiveS> implements SensitiveSService {

    @Override
    public int addSensitiveS(String word, int wordId) {
        QueryWrapper<SensitiveS> wrapper = new QueryWrapper<>();
        wrapper.eq("word_id",wordId);
        SensitiveS sensitive = baseMapper.selectOne(wrapper);
        //id是非必须的，那如果之前已经存在了的id，不进行覆盖。
        if (sensitive!=null){
            return 0;
        }else {
            SensitiveS newSensitiveS = new SensitiveS(wordId,word);
            return baseMapper.insert(newSensitiveS);
        }
    }

    @Override
    public int delSensitiveS(String word) {
        QueryWrapper<SensitiveS> wrapper = new QueryWrapper<>();
        //选出指定类型名称的帖子类型
        wrapper.eq("word",word);
        return baseMapper.delete(wrapper);
    }

    @Override
    public int updateSensitiveS(String word, int wordId) {
        SensitiveS sensitiveS = new SensitiveS();
        sensitiveS.setWordId(wordId);
        sensitiveS.setWord(word);
        return baseMapper.updateById(sensitiveS);
    }


    @Override
    public SensitiveS findOneByWord(String word) {
        QueryWrapper<SensitiveS> wrapper = new QueryWrapper<>();
        wrapper.eq("word",word);
        return baseMapper.selectOne(wrapper);
    }


    @Override
    public RestResult findSensitiveSPage(int current, int limit, SensitiveS sensitive) {
        Page<SensitiveS> page = new Page<>(current,limit);
        QueryWrapper<SensitiveS> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(sensitive.getWordId())){
            wrapper.like("word_id",sensitive.getWordId());
        }else if (!StringUtils.isEmpty(sensitive.getWord())){
            wrapper.like("word",sensitive.getWord());
        }
        baseMapper.selectPage(page,wrapper);
        List<SensitiveS> list = page.getRecords();
        long total = page.getTotal();
        long pages = page.getPages();
        return RestResult.ok().data("rows",list).data("total",total).
                data("pages",pages).data("current", current).data("limit", limit);
    }

    @Override
    public int findSensitiveSProportion(String postContent) {
        //拿到所有的敏感词元组[Sensitive(word_id=?, word=?)]
        List<SensitiveS> sensitives = baseMapper.selectList(null);
        //用来存放每个敏感词在内容中所占的字数
        int[] counts = new int[sensitives.size()];
        int sensitiveSum=0;
        //帖子的初始长度
        int originalLength;
        int newLength;
        //迭代敏感词
        for (int i = 0; i < sensitives.size(); i++) {
            originalLength=postContent.length();
            //拿到敏感词
            String word = sensitives.get(i).getWord();
            //用String的replace方法将敏感词置为空
            postContent = postContent.replace(word, "");
            //replace替换前后的长度之差即为敏感词所占的次数
            newLength=postContent.length();
            counts[i]=originalLength-newLength;
            sensitiveSum+=counts[i];
        }
        //返回的是一个百分比
        return sensitiveSum*100/postContent.length();
    }


    @Override
    public Map<Integer, Integer> findAllProportion(List<Post> posts) {
        //键是postId，值是敏感百分比
        Map<Integer, Integer> map = new HashMap<>(posts.size());
        for (Post post : posts) {
            int sensitiveProportion = findSensitiveSProportion(post.getContent());
            map.put(post.getPostId(),sensitiveProportion);
        }
        return map;
    }
}
