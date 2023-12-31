package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Post;
import com.team.xjwall.model.SensitiveS;
import com.team.xjwall.model.SensitiveS;
import com.team.xjwall.service.SensitiveSService;
import com.team.xjwall.service.SensitiveSService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 *  敏感词在整个系统中的具体体现：
 *  一、对帖子的检查（对单个帖子、对所有帖子）----本控制器实现
 *  二、管理员对sensitive（table）的增删改查功能----本控制器实现
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/sensitive")
public class SensitiveSController {
    @Autowired
    private SensitiveSService ss;

    @ApiOperation(value = "敏感词的增加")
    @PostMapping("/add")
    public RestResult add(@RequestBody SensitiveS sensitiveS){
        SensitiveS flag = ss.getById(sensitiveS.getWordId());
        if (flag==null){
            ss.save(sensitiveS);
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "敏感词的删除")
    @DeleteMapping("{word}")
    public RestResult delete(
            @ApiParam(name = "word",value = "敏感词",required = true)
            @PathVariable String word){
        SensitiveS sensitiveS = ss.findOneByWord(word);
        boolean flag = ss.removeById(sensitiveS.getWordId());
        if (flag){
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "敏感词的修改")
    @PostMapping("/update/{wordId}/{word}")
    public RestResult updateWord(
            @ApiParam(name = "wordid",value = "敏感词ID",required = true)
            @PathVariable int wordId,
            @ApiParam(name = "word",value = "敏感词",required = true)
            @PathVariable String word) {
        boolean flag = ss.updateById(new SensitiveS(wordId, word));
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation("敏感词的查询")
    @GetMapping("/getByWord/{word}")
    public RestResult getByWord(
            @ApiParam(name = "word",value = "敏感词",required = true)
            @PathVariable String word){
        SensitiveS sensitiveS = ss.findOneByWord(word);
        if (sensitiveS!=null){
            return RestResult.ok().data("sensitive",sensitiveS);
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "敏感词的分页查询")
    @PostMapping("/page/{current}/{limit}")
    public RestResult getPage(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable int current,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable int limit,
            @RequestBody(required = false) SensitiveS sensitiveS){
        return ss.findSensitiveSPage(current, limit, sensitiveS);
    }

    @ApiOperation(value = "帖子内容的敏感词汇占比")
    @PostMapping("/Proportion/{content}")
    public RestResult getProportion(
            @ApiParam(name = "content", value = "帖子内容概述", required = true)
            @PathVariable String content){
        int sensitiveSProportion = ss.findSensitiveSProportion(content);
        if (sensitiveSProportion>=0){
            return RestResult.ok().data("sensitiveProportion",sensitiveSProportion);
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "所有帖子内容的敏感词汇占比")
    @PostMapping("/Proportion/All/{posts}")
    public RestResult getAllProportion(
            @ApiParam(name = "posts", value = "所有帖子", required = true)
            @PathVariable List<Post> posts){
        Map<Integer, Integer> allProportion = ss.findAllProportion(posts);
        if (allProportion!=null){
            return RestResult.ok().data("map",allProportion);
        }else {
            return RestResult.error();
        }
    }

}

