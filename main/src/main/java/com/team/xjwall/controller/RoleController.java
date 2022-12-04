package com.team.xjwall.controller;


import com.team.xjwall.config.result.RestResult;
import com.team.xjwall.model.Role;
import com.team.xjwall.service.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author XXX
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService rs;
    @ApiOperation(value = "角色的增加")
    @PostMapping("/add")
    public RestResult add(@RequestBody Role role){//通过
        Role flag = rs.getById(role.getRoleId());
        if (flag==null){
            rs.save(role);
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "角色名的删除")
    @DeleteMapping("{roleName}")
    public RestResult delete(
            @ApiParam(name = "roleName",value = "角色名",required = true)
            @PathVariable String roleName){//通过
        Role role = rs.findOneByName(roleName);
        boolean flag = rs.removeById(role.getRoleId());
        if (flag){
            return RestResult.ok();
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "角色的修改")
    @PostMapping("/update/{roleId}/{roleName}")
    public RestResult updateWord(
            @ApiParam(name = "roleId",value = "角色ID",required = true)
            @PathVariable int roleId,
            @ApiParam(name = "roleName",value = "角色名",required = true)
            @PathVariable String roleName) {//通过
        boolean flag = rs.updateById(new Role(roleId, roleName));
        if (flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation("角色的查询")
    @GetMapping("/getByRoleName/{roleName}")
    public RestResult getByWord(
            @ApiParam(name = "roleName",value = "角色名",required = true)
            @PathVariable String roleName){//通过
        Role role = rs.findOneByName(roleName);
        if (role!=null){
            return RestResult.ok().data("role",role);
        }else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "角色的分页查询")
    @PostMapping("/page/{current}/{limit}")
    public RestResult getPage(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable int current,
            @ApiParam(name = "limit", value = "每页的记录数", required = true)
            @PathVariable int limit,
            @RequestBody(required = false) Role role){//通过
        return rs.findRolePage(current, limit, role);
    }

}

