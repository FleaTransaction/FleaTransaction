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

    @ApiOperation(value="判断商品是否被收藏")
    @GetMapping("/isfavourites")
    @RequiresAuthentication
    @CrossOrigin
    public Result isFavourites(@RequestParam("productid")int productid){
        UserFavourites userFavourites = userFavouritesService.getOne(new QueryWrapper<UserFavourites>()
                .eq("product_id",productid));
        if(userFavourites != null){
            if(ShiroUtils.getProfile().getUserId() == userFavourites.getUserId()){
                return Result.succ(200,"该商品已被用户收藏",userFavourites);
            }
        }
        return Result.succ("该商品没有被此用户收藏");
    }

    @ApiOperation(value="添加商品收藏")
    @PostMapping("/add")
    @RequiresAuthentication
    @CrossOrigin
    public Result addUserFavourites(@Validated @RequestBody UserFavourites userFavourites){
        userFavourites.setUserId(ShiroUtils.getProfile().getUserId());
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
    public Result removeUserFavourites(@RequestParam("favouritesid") int favouritesid){
        if(userFavouritesService.removeById(favouritesid)) {
            return Result.succ(200, "删除成功", null);
        }
        else{
            return Result.fail("删除失败");
        }
    }
    @ApiOperation(value="修改商品收藏")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modifyUserFavourites(@Validated @RequestBody UserFavourites userFavourites){
        userFavourites.setUserId(ShiroUtils.getProfile().getUserId());
        Product product=productService.getById(userFavourites.getProductId());
        if(product==null)
            return Result.fail("商品不存在，修改失败");
        if(userFavouritesService.updateById(userFavourites)) {
            return Result.succ(200, "修改成功", userFavourites);
        }
        else{
            return Result.fail("修改失败");
        }
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
