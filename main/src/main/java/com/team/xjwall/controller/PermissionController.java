package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Permission;
import com.team.xjwall.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService pms;

    @ApiOperation(value = "权限的增加")
    @PostMapping("/add")
    public RestResult add(@RequestBody Permission permission){//通过
        Permission flag = pms.getById(permission.getPermissionId());
        if (flag==null){
            pms.save(permission);
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "权限的删除")
    @DeleteMapping("{permissionName}")
    public RestResult delete(
            @ApiParam(name = "permissionName",value = "权限名",required = true)
            @PathVariable String permissionName){//通过
        Permission permission = pms.findOneByName(permissionName);
        boolean flag = pms.removeById(permission.getPermissionId());
        if (flag){
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "权限的修改")
    @PostMapping("/update/{permissionId}/{permissionName}/{operationId}")
    public RestResult updateWord(
            @ApiParam(name = "permissionId",value = "权限ID",required = true)
            @PathVariable int permissionId,
            @ApiParam(name = "permissionName",value = "权限名",required = true)
            @PathVariable String permissionName,
            @ApiParam(name = "operationId",value = "操作ID",required = true)
            @PathVariable int operationId) {//通过
        boolean flag = pms.updateById(new Permission(permissionId, permissionName,operationId));
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation("权限的查询")
    @GetMapping("/getByPermissionName/{permissionName}")
    public RestResult getByWord(
            @ApiParam(name = "permissionName",value = "权限名",required = true)
            @PathVariable String permissionName){//通过
        Permission permission = pms.findOneByName(permissionName);
        if (permission!=null){
            return RestResult.ok().data("permission",permission);
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "权限的分页查询")
    @PostMapping("/page/{current}/{limit}")
    public RestResult getPage(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable int current,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable int limit,
            @RequestBody(required = false) Permission permission){//通过
        return pms.findPermissionPage(current, limit, permission);
    }
}

