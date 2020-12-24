package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.ChildLabel;
import cn.fleatransaction.entity.ProDemand;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.entity.ProductLabel;
import cn.fleatransaction.service.IChildLabelService;
import cn.fleatransaction.service.IProDemandService;
import cn.fleatransaction.service.IProductLabelService;
import cn.fleatransaction.service.IProductService;
import cn.fleatransaction.service.impl.ProductLabelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="产品对应标签接口")
@RestController
@RequestMapping("/productLabel")
public class ProductLabelController {

    @Autowired
    IProductLabelService productLabelService;
    IChildLabelService childLabelService;
    IProductService productService;

    @ApiOperation(value="查询产品对应标签")
    @GetMapping("/query")
    public Result queryProductLabel(String productId){
        ProductLabel productLabel=productLabelService.getOne(new QueryWrapper<ProductLabel>().eq("product_id",productId));
        return Result.succ(200,"查询成功",productLabel);
    }
    @ApiOperation(value="添加产品对应标签")
    @PostMapping("/add")
    public Result addProductLabel(@Validated @RequestBody ProductLabel productLabel){
        Product product=productService.getOne(new QueryWrapper<Product>().eq("product_id",productLabel.getProductId()));
        if(product==null)
            return Result.fail("该产品不存在，添加失败");
        ChildLabel childLabel=childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_id",productLabel.getChildLabelId()));
        if(childLabel==null)
            return Result.fail("该标签不存在，添加失败");
        productLabelService.save(productLabel);
        return Result.succ(200,"添加成功",productLabel);
    }
    @ApiOperation(value="删除产品对应标签")
    @GetMapping("/remove")
    public Result removeProductLabel(@RequestBody ProductLabel productLabel){
        Product product=productService.getOne(new QueryWrapper<Product>().eq("product_id",productLabel.getProductId()));
        if(product!=null)
            return Result.fail("该产品还存在，删除失败");
        productLabelService.removeById(productLabel.getProductLabelId());
        return Result.succ(200,"删除成功",null);
    }
    @ApiOperation(value="修改产品对应标签")
    @PostMapping("/modify")
    public Result modifyProductLabel(@Validated @RequestBody ProductLabel productLabel){
        ChildLabel childLabel=childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_id",productLabel.getChildLabelId()));
        if(childLabel==null)
            return Result.fail("该标签不存在，修改失败");
        productLabelService.updateById(productLabel);
        return Result.succ(200,"修改成功",productLabel);
    }
    @ApiOperation(value="返回所有产品对应标签")
    @GetMapping("/list")
    public Result listProductLabel(){
        List<ProductLabel> productLabelList=productLabelService.list();
        return Result.succ(200,"返回成功",productLabelList);
    }

}
