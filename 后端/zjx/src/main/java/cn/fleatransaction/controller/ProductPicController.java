package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.entity.ProductPic;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.entity.UserOrder;
import cn.fleatransaction.service.IProductPicService;
import cn.fleatransaction.service.IProductService;
import cn.fleatransaction.service.IUserOrderService;
import cn.fleatransaction.util.UploadUtils;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags="产品图片接口")
@RestController
@RequestMapping("/productPic")
public class ProductPicController {

    @Autowired
    IProductPicService productPicService;

    @Autowired
    IProductService productService;

    @Autowired
    UploadUtils uploadUtils;

    @ApiOperation(value="查询指定产品的所有图片")
    @GetMapping("/query")
    public Result queryProductPic(int productId){
        List<ProductPic> productPicList=productPicService.list(new QueryWrapper<ProductPic>().eq("product_id",productId));
        return Result.succ(200,"查询成功",productPicList);
    }

    @ApiOperation(value="返回所有产品图片")
    @GetMapping("/list")
    public Result listProductPic(){
        List<ProductPic> productPicList= productPicService.list();
        return Result.succ(200,"查询成功",productPicList);
    }


    @ApiOperation(value="删除产品图片")
    @GetMapping("/remove")
    @RequiresAuthentication
    @CrossOrigin
    public Result removeProductPic(int pictureId){
        if(productPicService.removeById(pictureId)) {
            return Result.succ(200, "删除成功", null);
        }
        return Result.fail("删除失败");
    }

    @ApiOperation(value="修改产品图片")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modifyProductPic(@Validated @RequestBody ProductPic productPic){
        if(productPicService.updateById(productPic)) {
            return Result.succ(200, "修改成功", productPic);
        }
        return Result.fail("修改失败");
    }

    @PostMapping("/uploadproductpic")
    @ApiOperation(value = "商品图片上传")
    @RequiresAuthentication
    @CrossOrigin
    public Result upload(@RequestParam("imgfile") MultipartFile[] imgfile, int productid) {
        int count = imgfile.length;
        if (count <= 0) {
            return Result.fail("上传文件不能为空！");
        } else {
            for (MultipartFile multipartFile : imgfile) {
                String filename = multipartFile.getOriginalFilename();
                String prefix = filename.substring(filename.lastIndexOf(".") + 1);
                filename = UUID.randomUUID().toString().replace("-", "") + "." + prefix;

                File fileDir = uploadUtils.getProductDirFile();
                // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
                String url = fileDir.getAbsolutePath();
                try {
                    // 构建真实的文件路径
                    File newFile = new File(url + File.separator + filename);
                    //System.err.println(newFile.getAbsolutePath());
                    String urlpic = newFile.getAbsolutePath();
                    // 上传图片到 -》 “绝对路径”
                    multipartFile.transferTo(newFile);
                    ProductPic productpic = new ProductPic();
                    productpic.setProductPicture(urlpic);
                    productpic.setProductId(productid);
                    /**
                     * 保存图片
                     */
                    productPicService.saveOrUpdate(productpic);
                } catch (IOException e) {
                    e.printStackTrace();
                    return Result.fail("上传异常: " + e.getMessage());
                }
            }
            List<ProductPic> productPicList=productPicService.list(new QueryWrapper<ProductPic>().eq("product_id",productid));
            return Result.succ(200,"上传成功",productPicList);
        }
    }
}
