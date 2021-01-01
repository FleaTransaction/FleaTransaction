package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.Label;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.entity.UserMessage;
import cn.fleatransaction.service.ILabelService;
import cn.fleatransaction.service.IProductService;
import cn.fleatransaction.service.IUserMessageService;
import cn.fleatransaction.service.IUserService;
import cn.fleatransaction.util.ShiroUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="留言接口")
@RestController
@RequestMapping("/userMessage")
public class UserMessageController {

    @Autowired
    IUserMessageService userMessageService;

    @Autowired
    IUserService userService;

    @Autowired
    IProductService productService;


    @ApiOperation(value="查询指定产品的所有留言")
    @GetMapping("/query")
    public Result queryUserMessage(int productId){
        List<UserMessage> userMessageList=userMessageService.list(new QueryWrapper<UserMessage>().eq("product_id",productId));
        return Result.succ(200,"查询成功",userMessageList);
    }


    @ApiOperation(value="添加留言")
    @PostMapping("/add")
    @RequiresAuthentication
    @CrossOrigin
    public Result addUserMessage(@Validated @RequestBody UserMessage userMessage){
       userMessage.setUserId(ShiroUtils.getProfile().getUserId());
        User user=userService.getById(userMessage.getUserId());
       if(user==null)
           return Result.fail("该用户不存在，添加失败");
        Product product=productService.getById(userMessage.getProductId());
       if(product==null)
           return Result.fail("该产品不存在，添加失败");

       if(userMessageService.save(userMessage)) {
           return Result.succ(200, "添加成功", userMessage);
       }else{
           return Result.fail("添加失败");
       }
    }


    @ApiOperation(value="删除留言")
    @GetMapping("/remove")
    @RequiresAuthentication
    @CrossOrigin
    public Result removeUserMessage(int messageId){
        if(userMessageService.removeById(messageId)) {
            return Result.succ(200, "删除成功", null);
        }
        return Result.fail("删除失败");
    }

    @ApiOperation(value="修改留言")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modifyUserMessage(@Validated @RequestBody UserMessage userMessage){
        userMessage.setUserId(ShiroUtils.getProfile().getUserId());
        if(userMessageService.updateById(userMessage)) {
            return Result.succ(200, "修改成功", userMessage);
        }
        return Result.fail("修改失败");
    }
    @ApiOperation(value="返回所有留言")
    @GetMapping("/list")
    public Result listUserMessage(){
        List<UserMessage> userMessageList=userMessageService.list();
        return Result.succ(200,"返回成功",userMessageList);
    }

}
