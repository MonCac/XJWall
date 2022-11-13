package com.team.xjwall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Sensitive;
import com.team.xjwall.mapper.SensitiveMapper;
import com.team.xjwall.service.SensitiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@Service
public class SensitiveServiceImpl extends ServiceImpl<SensitiveMapper, Sensitive> implements SensitiveService {

    @Override
    public int addSensitive(String wordName, int wordId) {
        QueryWrapper<Sensitive> wrapper = new QueryWrapper<>();
        wrapper.eq("word_id",wordId);
        Sensitive sensitive = baseMapper.selectOne(wrapper);
        //id是非必须的，那如果之前已经存在了的id，不进行覆盖。
        if (sensitive!=null){
            return 0;
        }else {
            Sensitive newSensitive = new Sensitive(wordId,wordName);
            return baseMapper.insert(newSensitive);
        }
    }

    @Override
    public int delSensitive(String wordName) {
        QueryWrapper<Sensitive> wrapper = new QueryWrapper<>();
        //选出指定类型名称的帖子类型
        wrapper.eq("word",wordName);
        return baseMapper.delete(wrapper);
    }

    @Override
    public int updateSensitive(String wordName, int wordId) {
        Sensitive sensitive = new Sensitive();
        sensitive.setWordId(wordId);
        sensitive.setWord(wordName);
        return baseMapper.updateById(sensitive);
    }


    @Override
    public List<Sensitive> findByWordName(String wordName) {
        QueryWrapper<Sensitive> wrapper = new QueryWrapper<>();
        wrapper.like("word",wordName);
        return baseMapper.selectList(wrapper);
    }


    @Override
    public RestResult findSensitivePage(int current, int limit, Sensitive sensitive) {
        Page<Sensitive> page = new Page<>(current,limit);
        QueryWrapper<Sensitive> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(sensitive.getWordId())){
            wrapper.like("word_id",sensitive.getWordId());
        }else if (!StringUtils.isEmpty(sensitive.getWord())){
            wrapper.like("word",sensitive.getWord());
        }
        baseMapper.selectPage(page,wrapper);
        List<Sensitive> list = page.getRecords();
        long total = page.getTotal();
        long pages = page.getPages();
        return RestResult.ok().data("rows",list).data("total",total).
                data("pages",pages).data("current", current).data("limit", limit);
    }
}
