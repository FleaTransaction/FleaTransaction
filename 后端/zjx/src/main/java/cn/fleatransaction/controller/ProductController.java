package cn.fleatransaction.controller;

import cn.fleatransaction.common.Dot.productDto;
import cn.fleatransaction.common.Dot.productUpdate;
import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.*;
import cn.fleatransaction.service.*;
import cn.fleatransaction.util.ShiroUtils;
import cn.fleatransaction.util.UploadUtils;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Api(tags="产品接口")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    IUserService userService;

    @Autowired
    UploadUtils uploadUtils;

    @Autowired
    IProductPicService productPicService;

    @Autowired
    IProductLabelService productLabelService;

    @Autowired
    IChildLabelService childLabelService;

    @Autowired
    IUserMessageService userMessageService;

    @Autowired
    IUserFavouritesService userFavouritesService;

    @Autowired
    ILabelService labelService;

    @ApiOperation(value="查询产品")
    @GetMapping("/query")
    public Result queryProduct(String productName){
        Product product=productService.getOne(new QueryWrapper<Product>().like("product_name",productName));
        if(product == null){
            return Result.fail("没有该产品!");

        }else{
            return Result.succ(200,"查询成功",product);
        }
    }

    @ApiOperation(value="返回用户的所有商品")
    @GetMapping("/personalList")
    @RequiresAuthentication
    @CrossOrigin
    public Result personalList(){
        List<Product> productList= productService.list(new QueryWrapper<Product>()
                .eq("user_id", ShiroUtils.getProfile().getUserId()));
        if(productList == null) {
            return Result.fail("暂无商品！");
        }else{
            return Result.succ(200, "查询成功", productList);
        }
    }

    @ApiOperation(value="返回所有商品")
    @GetMapping("/list")
    public Result list(){
        List<Product> productList= productService.list();
        if(productList == null) {
            return Result.fail("暂无商品！");
        }else{
            return Result.succ(200, "查询成功", productList);
        }
    }

    @ApiOperation(value="添加商品")
    @PostMapping("/add")
    @RequiresAuthentication
    @CrossOrigin
    public Result add(@RequestParam("productname") String productname,
                      @RequestParam("productprice") Float productprice,
                      @RequestParam("productdescription") String productdescription,
                      @RequestParam("imgfile") MultipartFile[] imgfile,
                      @RequestParam("productlabel") String productlabel,
                      @RequestParam(value = "productphone",required = true,defaultValue = "null") String productphone,
                      @RequestParam(value = "productwechat",required = true,defaultValue = "null") String productwechat,
                      @RequestParam(value = "productqq",required = true,defaultValue = "null") String productqq) {
        Product product = new Product();
        product.setUserId(ShiroUtils.getProfile().getUserId());
        User user = userService.findUser(ShiroUtils.getProfile().getUserId());
        if(productphone.equals("null")){
            product.setProductPhone(user.getUserPhone());
        }
        product.setProductName(productname);
        product.setProductPrice(productprice);
        product.setProductDescription(productdescription);
        product.setProductWeChat(productwechat);
        product.setProductQq(productqq);
        productService.saveProduct(product);
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
                    String te = "http://47.110.243.78:8080/images/product/";
                    File newFile = new File(url + File.separator + filename);
                    //System.err.println(newFile.getAbsolutePath());
                    String urlpic = te + filename;
                    System.out.println(urlpic);
                    // 上传图片到 -》 “绝对路径”
                    multipartFile.transferTo(newFile);
                    ProductPic productpic = new ProductPic();
                    productpic.setProductPicture(urlpic);
                    productpic.setProductId(product.getProductId());
                    /**
                     * 保存图片
                     */
                    productPicService.save(productpic);
                } catch (IOException e) {
                    e.printStackTrace();
                    return Result.fail("上传异常: " + e.getMessage());
                }
            }
        }
        ChildLabel childLabel = childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_name",productlabel));
        ProductLabel pl = new ProductLabel();
        pl.setChildLabelId(childLabel.getChildLabelId());
        pl.setProductId(product.getProductId());
        if(!productLabelService.save(pl)){
            return Result.fail("标签新增失败!");
        }
        Object temp = productService.getProductInfoById(product.getProductId());
        return Result.succ(200, "添加成功", temp);
    }

    @ApiOperation(value="删除商品")
    @GetMapping("/remove")
    @RequiresAuthentication
    @CrossOrigin
    public Result remove(int productId){

        List<ProductLabel> productLabel = productLabelService.list(new QueryWrapper<ProductLabel>()
                .eq("product_id",productId));
        if(productLabel!= null){
            for(ProductLabel productLabel1 : productLabel) {
                productLabelService.removeById(productLabel1.getProductLabelId());
            }
        }

        List<UserMessage> userMessage = userMessageService.list(new QueryWrapper<UserMessage>()
                .eq("product_id",productId));
        if(userMessage!=null){
            for(UserMessage temp : userMessage) {
                userMessageService.removeById(temp.getMessageId());
            }
        }

        UserFavourites userFavourites = userFavouritesService.getOne(new QueryWrapper<UserFavourites>()
                .eq("product_id",productId));
        if(userFavourites!=null){
            userFavouritesService.removeById(userFavourites.getFavouritesId());
        }

        List<ProductPic> propic = productPicService.list(new QueryWrapper<ProductPic>()
                .eq("product_id",productId));
        if(propic!=null){
            for(ProductPic temp : propic){
                productPicService.removeById(temp.getPictureId());
            }
        }

        productService.remove(new QueryWrapper<Product>().eq("product_id",productId));
        Product product = productService.getById(productId);
        if(product == null) {
            return Result.succ(200, "删除成功", null);
        }else{
            return Result.fail("系统异常，删除出错，稍后再试!");
        }
    }

    @ApiOperation(value="修改商品")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modify(@RequestParam("productid") int productid,
                         @RequestParam("productname") String productname,
                         @RequestParam("productprice") Float productprice,
                         @RequestParam("productdescription") String productdescription,
                         @RequestParam("imgfile") MultipartFile[] imgfile,
                         @RequestParam("productlabel") String productlabel,
                         @RequestParam(value = "productphone",required = true,defaultValue = "null") String productphone,
                         @RequestParam(value = "productwechat",required = true,defaultValue = "null") String productwechat,
                         @RequestParam(value = "productqq",required = true,defaultValue = "null") String productqq){

        Product product = new Product();
        product.setUserId(ShiroUtils.getProfile().getUserId());
        User user = userService.findUser(ShiroUtils.getProfile().getUserId());
        if(productphone.equals("null")){
            product.setProductPhone(user.getUserPhone());
        }
        product.setProductId(productid);
        product.setProductName(productname);
        product.setProductPrice(productprice);
        product.setProductDescription(productdescription);
        product.setProductWeChat(productwechat);
        product.setProductQq(productqq);
        if(!productService.updateById(product)){
            return Result.fail("产品修改失败");
        }
        ChildLabel childLabel = childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_name",productlabel));
        ProductLabel pl = new ProductLabel();
        pl.setChildLabelId(childLabel.getChildLabelId());
        pl.setProductId(productid);
        if(!productLabelService.update(pl,new UpdateWrapper<ProductLabel>()
                .eq("product_id",productid))){
            return Result.fail("标签修改失败!");
        }
        if(!productPicService.remove(new QueryWrapper<ProductPic>()
                .eq("product_id",productid))){
            return Result.fail("图片修改失败");
        }
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
                    String te = "http://47.110.243.78:8080/images/product/";
                    File newFile = new File(url + File.separator + filename);
                    //System.err.println(newFile.getAbsolutePath());
                    String urlpic = te + filename;
                    //System.out.println(urlpic);
                    // 上传图片到 -》 “绝对路径”
                    multipartFile.transferTo(newFile);
                    ProductPic productpic = new ProductPic();
                    productpic.setProductPicture(urlpic);
                    productpic.setProductId(product.getProductId());
                    /**
                     * 保存图片
                     */
                    if(!productPicService.save(productpic)){
                        return Result.fail("图片修改失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return Result.fail("上传异常: " + e.getMessage());
                }
            }
        }

        List<productUpdate> productUpdate = new ArrayList<>();
        List<productDto> te = productService.getProductInfoById(product.getProductId());
        for(productDto t : te){
            Label label = labelService.getOne(new QueryWrapper<Label>()
                    .eq("label_id",childLabel.getLabelId()));
            productUpdate temp = new productUpdate();
            temp.setProductId(t.getProductId());
            temp.setProductName(t.getProductName());
            temp.setProductDescription(t.getProductDescription());
            temp.setProductPhone(t.getProductPhone());
            temp.setProductQq(t.getProductQq());
            temp.setProductWeChat(t.getProductWeChat());
            temp.setChildlabel(childLabel.getChildLabelName());
            temp.setLabelname(label.getLabelName());
            temp.setProductPicture(t.getProductPicture());
            temp.setProductPrice(t.getProductPrice());
            temp.setProductTime(t.getProductTime());
            temp.setUserId(t.getUserId());
            productUpdate.add(temp);
        }

        return Result.succ(productUpdate);
    }


}
