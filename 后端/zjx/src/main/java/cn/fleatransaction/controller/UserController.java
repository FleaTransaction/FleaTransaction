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
    public User queryStudent(Integer id){
        User oneuser = userService.findUser(id);
        return oneuser;
    }


    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public List<User> queryAllUser(){
        return userService.list();
    }


    @ApiOperation(value = "获取用户数量")
    @GetMapping("/count")
    public Integer count(){
        return userService.count();
    }

    @ApiOperation(value = "保存用户信息")
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){

        return Result.succ(user);
    }
    

}
