package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.service.IProductService;
import cn.fleatransaction.util.UploadUtils;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags="产品接口")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    IProductService productService;

    @ApiOperation(value="查询产品")
    @GetMapping("/query")
    public Result queryStudent(String productName){
        Product product=productService.getOne(new QueryWrapper<Product>().like("product_name",productName));
        return Result.succ(200,"查询成功",product);
    }

    @ApiOperation(value="返回用户的所有商品")
    @GetMapping("/personalList")
    public Result personalList(String userId){
        List<Product> productList= productService.list(new QueryWrapper<Product>().eq("user_id",userId));
        return Result.succ(200,"查询成功",productList);
    }

    @ApiOperation(value="返回所有商品")
    @GetMapping("/list")
    public Result list(){
        List<Product> productList= productService.list();
        return Result.succ(200,"查询成功",productList);
    }

    @ApiOperation(value="添加商品")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody Product product){
        productService.save(product);
        return Result.succ(200,"添加成功",product);
    }

    @ApiOperation(value="删除商品")
    @GetMapping("/remove")
    public Result remove(String productId){
        productService.remove(new QueryWrapper<Product>().eq("product_id",productId));
        return Result.succ(200,"删除成功",null);
    }

    @ApiOperation(value="修改商品")
    @PostMapping("/modify")
    public Result modify(@Validated @RequestBody Product product){
        productService.updateById(product);
        return Result.succ(200,"修改成功",product);
    }


}
