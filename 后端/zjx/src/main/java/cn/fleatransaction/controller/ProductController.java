package cn.fleatransaction.controller;

import cn.fleatransaction.common.Dot.OneProductDto;
import cn.fleatransaction.common.Dot.productDto;
import cn.fleatransaction.common.Dot.productUpdate;
import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.*;
import cn.fleatransaction.service.*;
import cn.fleatransaction.util.ListUtils;
import cn.fleatransaction.util.ShiroUtils;
import cn.fleatransaction.util.UploadUtils;
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
import java.util.*;
import java.util.stream.Collectors;

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
    ListUtils listUtils;

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
        List<Product> product = new ArrayList<>();
        List<Product> product_name=productService.list(new QueryWrapper<Product>()
                .like("product_name",productName));
        if(product_name != null){
            product.addAll(product_name);
        }

        List<Product> productdescription = productService.list(new QueryWrapper<Product>()
                .like("product_description",productName));
        if(productdescription != null){
            product.addAll(productdescription);
        }

        List<ChildLabel> childLabels = childLabelService.list(new QueryWrapper<ChildLabel>()
                .like("child_label_name",productName));
        List<ProductLabel> productLabelList = new ArrayList<>();
        if(childLabels != null){
            for(ChildLabel t : childLabels) {
                List<ProductLabel> productLabels = productLabelService.list(new QueryWrapper<ProductLabel>()
                        .eq("child_label_id",t.getChildLabelId()));
                productLabelList.addAll(productLabels);
            }
        }
        if(productLabelList!=null) {
            for(ProductLabel te : productLabelList) {
                List<Product> productlabel = productService.list(new QueryWrapper<Product>()
                        .eq("product_id",te.getProductId()));
                product.addAll(productlabel);
            }
        }

        //List<Product> temp = listUtils.distinctByProductId(product);
        List temp = product.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(s -> s.getProductId()))), ArrayList::new));

        if(temp == null){
            return Result.fail("没有该产品!");

        }else{
            return Result.succ(200,"查询成功",temp);
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

    @ApiOperation(value="返回所有商品根据浏览量排序")
    @GetMapping("/listByCount")
    public Result listbycount(){
        List<Product> productList= productService.list();
        if(productList == null) {
            return Result.fail("暂无商品！");
        }else{
            return Result.succ(200, "查询成功", productList);
        }
    }

    @ApiOperation(value = "APP添加商品图片返回图片URL")
    @PostMapping("/appPictureAdd")
    @RequiresAuthentication
    @CrossOrigin
    public Result picadd(@RequestParam("imgfile") MultipartFile[] imgfile){
        List<String> picurl = new ArrayList<>();
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
                    picurl.add(urlpic);
                    /**
                     * 保存图片
                     */
                } catch (IOException e) {
                    e.printStackTrace();
                    return Result.fail("上传异常: " + e.getMessage());
                }
            }
        }
        return Result.succ(picurl);
    }

    @ApiOperation(value = "APP添加商品")
    @PostMapping("/appProductAdd")
    @RequiresAuthentication
    @CrossOrigin
    public Result appadd(@Validated @RequestBody productUpdate product){
        product.setUserId(ShiroUtils.getProfile().getUserId());
        Product product1 = new Product();
        product1.setProductName(product.getProductName());
        product1.setProductDescription(product.getProductDescription());
        product1.setProductPhone(product.getProductPhone());
        product1.setProductPrice(product.getProductPrice());
        product1.setProductQq(product.getProductQq());
        product1.setProductWeChat(product.getProductWeChat());
        product1.setUserId(product.getUserId());
        productService.saveProduct(product1);

        for(String url : product.getProductPicture()){
            ProductPic productPic = new ProductPic();
            productPic.setProductId(product1.getProductId());
            productPic.setProductPicture(url);
            if(!productPicService.save(productPic)){
                return Result.fail("商品图片修改失败");
            }
        }

        ChildLabel childLabel = childLabelService.getOne(new QueryWrapper<ChildLabel>()
                .eq("child_label_name",product.getChildlabel()));
        ProductLabel productLabel = new ProductLabel();
        productLabel.setProductId(product1.getProductId());
        productLabel.setChildLabelId(childLabel.getChildLabelId());
        if(!productLabelService.save(productLabel)){
            return Result.fail("商品标签添加失败");
        }

        OneProductDto productDtoList=productService.getProductInfoById(product1.getProductId());
        if(productDtoList == null){
            return Result.fail("商品添加失败");
        }
        return Result.succ(200,"商品添加成功",productDtoList);
    }

    @ApiOperation(value = "APP修改商品")
    @PostMapping("/appProductUpdate")
    @RequiresAuthentication
    @CrossOrigin
    public Result appProductUpdate(@Validated @RequestBody productUpdate product){
        product.setUserId(ShiroUtils.getProfile().getUserId());
        Product product1 = new Product();
        product1.setProductName(product.getProductName());
        product1.setProductDescription(product.getProductDescription());
        product1.setProductPhone(product.getProductPhone());
        product1.setProductPrice(product.getProductPrice());
        product1.setProductQq(product.getProductQq());
        product1.setProductWeChat(product.getProductWeChat());
        product1.setUserId(product.getUserId());

        ChildLabel childLabel = childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_name",product.getChildlabel()));
        ProductLabel pl = new ProductLabel();
        pl.setChildLabelId(childLabel.getChildLabelId());
        pl.setProductId(product.getProductId());
        if(!productLabelService.update(pl,new UpdateWrapper<ProductLabel>()
                .eq("product_id",product.getProductId()))){
            return Result.fail("标签修改失败!");
        }

        List<ProductPic> productPic = productPicService.list(new QueryWrapper<ProductPic>()
                .eq("product_id",product.getProductId()));

        List<String> te = new ArrayList<>();
        for(ProductPic tm : productPic){
            te.add(tm.getProductPicture());
        }

        for(String tp : te){
            if(!productPicService.remove(new QueryWrapper<ProductPic>()
                    .eq("product_picture",tp))){
                return Result.fail("图片修改失败");
            }
        }

        for(String temp : product.getProductPicture()){
            ProductPic pic = new ProductPic();
            pic.setProductId(product.getProductId());
            pic.setProductPicture(temp);
            if(!productPicService.save(pic)){
                return Result.fail("图片修改失败");
            }
        }

        productService.updateById(product1);

        OneProductDto productDtoList=productService.getProductInfoById(product1.getProductId());
        if(productDtoList == null){
            return Result.fail("商品修改失败");
        }
        return Result.succ(200,"商品修改成功",productDtoList);
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
        product.setProductPhone(productphone);
        product.setProductName(productname);
        product.setProductPrice(productprice);
        product.setProductDescription(productdescription);
        product.setProductWeChat(productwechat);
        product.setProductQq(productqq);
        int count = imgfile.length;
        if (count <= 0) {
            return Result.fail("上传文件不能为空！");
        } else {
            productService.saveProduct(product);
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
    @PostMapping("/modifybyparm")
    @RequiresAuthentication
    @CrossOrigin
    public Result modify(@RequestParam("productid") int productid,
                         @RequestParam("productname") String productname,
                         @RequestParam("productprice") Float productprice,
                         @RequestParam("productdescription") String productdescription,
                         @RequestParam(value = "imgfile",required = false) MultipartFile[] imgfile,
                         @RequestParam("productlabel") String productlabel,
                         @RequestParam(value = "productphone",required = true,defaultValue = "null") String productphone,
                         @RequestParam(value = "productwechat",required = true,defaultValue = "null") String productwechat,
                         @RequestParam(value = "productqq",required = true,defaultValue = "null") String productqq,
                         @RequestParam(value = "picurl") String[] picurl){

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
        ChildLabel childLabel = childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_name",productlabel));
        ProductLabel pl = new ProductLabel();
        pl.setChildLabelId(childLabel.getChildLabelId());
        pl.setProductId(productid);
        if(!productLabelService.update(pl,new UpdateWrapper<ProductLabel>()
                .eq("product_id",productid))){
            return Result.fail("标签修改失败!");
        }

        List<ProductPic> productPic = productPicService.list(new QueryWrapper<ProductPic>()
                .eq("product_id",productid));

        List<String> tm =new ArrayList<String>();
        for(ProductPic pic : productPic){
            int flag = 0;
            for(String t : picurl){
                if(pic.getProductPicture().equals(t)){
                    flag = 1;
                }
            }
            if(flag == 0){
                tm.add(pic.getProductPicture());
            }
        }

        for(String tp : tm){
            if(!productPicService.remove(new QueryWrapper<ProductPic>()
                    .eq("product_picture",tp))){
                return Result.fail("图片修改失败");
            }
        }

        int count = imgfile.length;
        if (count > 0) {
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

        if(!productService.updateById(product)){
            return Result.fail("产品修改失败");
        }

        List<productUpdate> productUpdate = new ArrayList<>();
        OneProductDto te = productService.getProductInfoById(product.getProductId());

        Label label = labelService.getOne(new QueryWrapper<Label>()
                .eq("label_id",childLabel.getLabelId()));
        productUpdate temp = new productUpdate();
        temp.setProductId(te.getProductId());
        temp.setProductName(te.getProductName());
        temp.setProductDescription(te.getProductDescription());
        temp.setProductPhone(te.getProductPhone());
        temp.setProductQq(te.getProductQq());
        temp.setProductWeChat(te.getProductWeChat());
        temp.setChildlabel(childLabel.getChildLabelName());
        temp.setLabelname(label.getLabelName());
        temp.setProductPicture(te.getProductPicture());
        temp.setProductPrice(te.getProductPrice());
        temp.setProductTime(te.getProductTime());
        temp.setUserId(te.getUserId());
        productUpdate.add(temp);

        return Result.succ(productUpdate);
    }

    @ApiOperation(value="修改商品浏览")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modify(@Validated @RequestBody Product product) {
        //product.setUserId(ShiroUtils.getProfile().getUserId());
        if (productService.updateById(product)) {
            Product temp = productService.getById(product.getProductId());
            return Result.succ(200, "修改成功", temp);
        }
        return Result.fail("修改失败，稍后再试!");
    }

}
