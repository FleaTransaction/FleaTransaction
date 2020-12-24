package cn.fleatransaction.controller;





import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;


    @ApiOperation(value = "查询用户")
    @GetMapping("/query")
    public Result queryStudent(Integer id){
        User oneuser = userService.findUser(id);
        return Result.succ(200,"查询成功",oneuser);
    }


    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public Result queryAllUser(){
        List<User> userList=userService.list();
        return Result.succ(200,"获取成功",userList);
    }


    @ApiOperation(value = "获取用户数量")
    @GetMapping("/count")
    public Result count(){
        int count=userService.count();
        return Result.succ(200,"获取成功",count);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/modify")
    public Result save(@Validated @RequestBody User user){

        userService.save(user);
        return Result.succ(200,"修改成功",user);
    }
    

}
