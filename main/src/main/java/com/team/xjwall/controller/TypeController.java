package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Type;
import com.team.xjwall.service.TypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 帖子类型表 前端控制器
 * 帖子类型在整个系统中的具体体现:
 * 一、type_name->type_id——>post元组----由PostController实现
 * 二、管理员对type（table）的增删改查功能----本控制器实现
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService ts;

    @ApiOperation(value = "帖子类型的增加")
    @PostMapping("/add")
    public RestResult add(@RequestBody Type type){//增加
        Type flag = ts.getById(type.getTypeId());
        if (flag==null){
            ts.save(type);
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "帖子类型的删除")
    @DeleteMapping("{typeName}")
    public RestResult delete(
            @ApiParam(name = "typename",value = "帖子类型名称",required = true)
            @PathVariable String typeName){//通过
        int typeId = ts.findByTypeName(typeName);
        boolean flag = ts.removeById(typeId);
        if (flag){
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "帖子类型的修改")
    @PostMapping("/update/{typeId}/{typeName}")
    public RestResult updateTypeName(
            @ApiParam(name = "typeid",value = "帖子类型ID",required = true)
            @PathVariable int typeId,
            @ApiParam(name = "typename",value = "帖子类型名称",required = true)
            @PathVariable String typeName) {//通过
        boolean flag = ts.updateById(new Type(typeId, typeName));
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation("帖子类型的查询")
    @GetMapping("/getByTypeName/{typeName}")
    public RestResult getByTypeName(
            @ApiParam(name = "typename",value = "类型的名称",required = true)
            @PathVariable String typeName){//通过
        Type type = ts.findOneByTypeName(typeName);
        if (type!=null){
            return RestResult.ok().data("type",type);
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "帖子类型的分页查询")
    @PostMapping("/page/{current}/{limit}")
    public RestResult getPage(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable int current,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable int limit,
            @RequestBody(required = false) Type type){
        return ts.findTypePage(current, limit, type);
    }
}

