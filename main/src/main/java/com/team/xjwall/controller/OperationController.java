package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Operation;
import com.team.xjwall.service.OperationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 操作表 前端控制器
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/operation")
public class OperationController {
    @Autowired
    private OperationService ops;

    @ApiOperation(value = "操作的增加")
    @PostMapping("/add")
    public RestResult add(@RequestBody Operation operation){ //通过
        Operation flag = ops.getById(operation.getOperationId());
        if (flag==null){
            ops.save(operation);
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "操作的删除")
    @DeleteMapping("{operationName}")
    public RestResult delete(
            @ApiParam(name = "operationName",value = "操作名",required = true)
            @PathVariable String operationName){//通过
        Operation operation = ops.findOneByName(operationName);
        boolean flag = ops.removeById(operation.getOperationId());
        if (flag){
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "操作的修改")
    @PostMapping("/update/{operationId}/{operationName}")
    public RestResult updateWord(
            @ApiParam(name = "operationId",value = "操作ID",required = true)
            @PathVariable int operationId,
            @ApiParam(name = "operationName",value = "操作名",required = true)
            @PathVariable String operationName) {//通过
        boolean flag = ops.updateById(new Operation(operationId, operationName));
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation("操作的查询")
    @GetMapping("/getByOperationName/{operationName}")
    public RestResult getByWord(
            @ApiParam(name = "operationName",value = "操作名",required = true)
            @PathVariable String operationName){//通过
        Operation operation = ops.findOneByName(operationName);
        if (operation!=null){
            return RestResult.ok().data("operation",operation);
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "操作的分页查询")
    @PostMapping("/page/{current}/{limit}")
    public RestResult getPage(//通过
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable int current,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable int limit,
            @RequestBody(required = false) Operation operation){
        return ops.findOperationPage(current, limit, operation);
    }
}

