package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.entity.UserOrder;
import cn.fleatransaction.service.IProductService;
import cn.fleatransaction.service.IUserOrderService;
import cn.fleatransaction.service.impl.ProductService;
import cn.fleatransaction.service.impl.UserOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="订单接口")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IUserOrderService userOrderService;

    @Autowired
    IProductService productService;

    @ApiOperation(value="查询订单")
    @GetMapping("/query")
    public Result queryOrder(String orderId){
        UserOrder userOrder= userOrderService.getOne(new QueryWrapper<UserOrder>().eq("order_id",orderId));
        return Result.succ(200,"查询成功",userOrder);
    }

    @ApiOperation(value="返回用户的所有订单")
    @GetMapping("/personalList")
    public Result personalList(String userId){
        List<UserOrder> orderList= userOrderService.list(new QueryWrapper<UserOrder>().eq("user_id",userId));
        return Result.succ(200,"查询成功",orderList);
    }

    @ApiOperation(value="返回所有订单")
    @GetMapping("/list")
    public Result list(){
        List<UserOrder> orderList= userOrderService.list();
        return Result.succ(200,"查询成功",orderList);
    }

    @ApiOperation(value="添加订单")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody UserOrder userOrder){
        UserOrder userOrder1=userOrderService.getOne(new QueryWrapper<UserOrder>().eq("product_id",userOrder.getProductId()));
        if(userOrder1!=null)
            return Result.fail(400,"该商品已售罄，订单添加失败",null);
        Product product= productService.getOne(new QueryWrapper<Product>().eq("product_id",userOrder.getProductId()));
        if(product==null)
            return Result.fail(400,"该商品不存在，订单添加失败",null);

        userOrderService.save(userOrder);
        return Result.succ(200,"添加成功",userOrder);
    }

    @ApiOperation(value="删除订单")
    @GetMapping("/remove")
    public Result remove(String orderId){
        userOrderService.remove(new QueryWrapper<UserOrder>().eq("order_id",orderId));
        return Result.succ(200,"删除成功",null);
    }

    @ApiOperation(value="修改订单")
    @PostMapping("/modify")
    public Result modify(@Validated @RequestBody UserOrder userOrder){
        UserOrder userOrder1=userOrderService.getOne(new QueryWrapper<UserOrder>().eq("product_id",userOrder.getProductId()));
        if(userOrder1!=null)
            return Result.fail(400,"该商品已售罄，订单修改失败",null);
        Product product= productService.getOne(new QueryWrapper<Product>().eq("product_id",userOrder.getProductId()));
        if(product==null)
            return Result.fail(400,"该商品不存在，订单修改失败",null);
        userOrderService.updateById(userOrder);
        return Result.succ(200,"修改成功",userOrder);
    }

}
