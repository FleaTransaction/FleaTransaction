package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.entity.ProductPic;
import cn.fleatransaction.entity.UserOrder;
import cn.fleatransaction.service.IProductPicService;
import cn.fleatransaction.service.IProductService;
import cn.fleatransaction.service.IUserOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="产品图片接口")
@RestController
@RequestMapping("/productPic")
public class ProductPicController {

    @Autowired
    IProductPicService productPicService;

    @Autowired
    IProductService productService;

    @ApiOperation(value="查询指定产品的所有图片")
    @GetMapping("/query")
    public Result queryProductPic(String productId){
        List<ProductPic> productPicList=productPicService.list(new QueryWrapper<ProductPic>().eq("product_id",productId));
        return Result.succ(200,"查询成功",productPicList);
    }

    @ApiOperation(value="返回所有产品图片")
    @GetMapping("/list")
    public Result listProductPic(){
        List<ProductPic> productPicList= productPicService.list();
        return Result.succ(200,"查询成功",productPicList);
    }

    @ApiOperation(value="添加产品图片")
    @PostMapping("/add")
    public Result addProductPic(@Validated @RequestBody ProductPic productPic){
        Product product= productService.getOne(new QueryWrapper<Product>().eq("product_id",productPic.getProductId()));
        if(product==null)
            return Result.fail(400,"该商品不存在，订单图片添加失败",null);

        productPicService.save(productPic);
        return Result.succ(200,"添加成功",productPic);
    }

    @ApiOperation(value="删除产品图片")
    @GetMapping("/remove")
    public Result removeProductPic(int pictureId){
        productPicService.removeById(pictureId);
        return Result.succ(200,"删除成功",null);
    }

    @ApiOperation(value="修改产品图片")
    @PostMapping("/modify")
    public Result modifyProductPic(@Validated @RequestBody ProductPic productPic){
        productPicService.updateById(productPic);
        return Result.succ(200,"修改成功",productPic);
    }

}
