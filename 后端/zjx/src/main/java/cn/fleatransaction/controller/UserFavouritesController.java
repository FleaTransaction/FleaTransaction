package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.Label;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.entity.UserFavourites;
import cn.fleatransaction.service.ILabelService;
import cn.fleatransaction.service.IProductService;
import cn.fleatransaction.service.IUserFavouritesService;
import cn.fleatransaction.service.IUserService;
import cn.fleatransaction.util.ShiroUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="商品收藏接口")
@RestController
@RequestMapping("/userFavourites")
public class UserFavouritesController {

    @Autowired
    IUserFavouritesService userFavouritesService;

    @Autowired
    IUserService userService;

    @Autowired
    IProductService productService;

    @ApiOperation(value="查询用户个人的商品收藏")
    @GetMapping("/query")
    @RequiresAuthentication
    @CrossOrigin
    public Result queryUserFavourites(){
        int userId = ShiroUtils.getProfile().getUserId();
        List<UserFavourites> userFavouritesList=userFavouritesService.list(new QueryWrapper<UserFavourites>().eq("user_id",userId));
        if(userFavouritesList != null) {
            return Result.succ(200, "查询成功", userFavouritesList);
        }
        return Result.fail("查询失败");
    }

    @ApiOperation(value="添加商品收藏")
    @PostMapping("/add")
    @RequiresAuthentication
    @CrossOrigin
    public Result addUserFavourites(@Validated @RequestBody UserFavourites userFavourites){
        User user=userService.getById(userFavourites.getUserId());
        if(user==null)
            return Result.fail("用户不存在，添加失败");
        Product product=productService.getById(userFavourites.getProductId());
        if(product==null)
            return Result.fail("商品不存在，添加失败");

       userFavouritesService.save(userFavourites);
        return Result.succ(200,"添加成功",userFavourites);
    }
    @ApiOperation(value="删除商品收藏")
    @GetMapping("/remove")
    @RequiresAuthentication
    @CrossOrigin
    public Result removeUserFavourites(@RequestBody UserFavourites userFavourites){
        userFavouritesService.remove(new QueryWrapper<UserFavourites>().eq("favourites_id",userFavourites.getFavouritesId()));
        return Result.succ(200,"删除成功",null);
    }
    @ApiOperation(value="修改商品收藏")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modifyUserFavourites(@Validated @RequestBody UserFavourites userFavourites){
        Product product=productService.getById(userFavourites.getProductId());
        if(product==null)
            return Result.fail("商品不存在，修改失败");
        userFavouritesService.updateById(userFavourites);
        return Result.succ(200,"修改成功",userFavourites);
    }
    @ApiOperation(value="返回用户所有商品收藏")
    @GetMapping("/list")
    @RequiresAuthentication
    @CrossOrigin
    public Result listUserFavourites(){
        List<UserFavourites> userFavouritesList=userFavouritesService.list(new QueryWrapper<UserFavourites>()
                .eq("user_id",ShiroUtils.getProfile().getUserId()));
        if(userFavouritesList == null){
            return Result.succ(200,"返回成功",null);
        }
        return Result.succ(200,"返回成功",userFavouritesList);
    }

}
