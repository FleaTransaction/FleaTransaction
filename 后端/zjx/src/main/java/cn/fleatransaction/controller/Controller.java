package cn.fleatransaction.controller;

import cn.fleatransaction.common.Dot.labelDto;
import cn.fleatransaction.common.Dot.messageDto;
import cn.fleatransaction.common.Dot.productDto;
import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags="提供给前端的工具接口")
@RestController
@RequestMapping("/tools")
public class Controller {


    @Autowired
    IProductService productService;


    @ApiOperation(value="返回标签及对应子标签")
    @GetMapping("/labelList")
    public Result listChildLabel(){
        List<String> labelList= productService.getLabel();
        //System.out.print(labelList);
        int size=labelList.size();
        //System.out.print(size);
        List<labelDto> labelDtoList=new ArrayList<labelDto>(200);
        for(int i=0;i<size;i++){
            List<String> childLabelList=new ArrayList<>();
            childLabelList=productService.getChildLabel(labelList.get(i));
            labelDto labelDto=new labelDto();
            labelDto.setLabelName(labelList.get(i));
            labelDto.setChildLabelName(childLabelList);
            labelDtoList.add(labelDto);
        }
        return cn.fleatransaction.common.lang.Result.succ(200,"返回成功",labelDtoList);
    }
    @ApiOperation(value="返回商品信息")
    @GetMapping("/productList")
    Result listProductInfo(){
        List<productDto> productDtoList=productService.getProductInfo();
        if(productDtoList == null){
            return Result.succ(200,"返回成功,暂无商品",null);
        }
        return Result.succ(200,"返回成功",productDtoList);
    }
    @ApiOperation(value="返回指定标签的商品信息")
    @GetMapping("/queryProduct")
    Result queryProductInfo(String labelName,String childLabelName){
        List<productDto> productDtoList=productService.queryProductInfo(labelName,childLabelName);
        if(productDtoList == null){
            return Result.succ(200,"返回成功,暂无商品",null);
        }
        return Result.succ(200,"返回成功",productDtoList);
    }

    @ApiOperation(value="返回指定ID的商品信息")
    @GetMapping("/queryProductById")
    Result getProductInfoById(int productId){
        List<productDto> productDtoList=productService.getProductInfoById(productId);
        if(productDtoList == null){
            return Result.succ(200,"返回成功,该商品不存在",null);
        }
        return Result.succ(200,"返回成功",productDtoList);
    }

    @ApiOperation(value="返回指定商品ID的所有留言信息")
    @GetMapping("/queryMessageById")
    Result getMessageById(int productId){
        List<messageDto> productDtoList=productService.getMessageById(productId);
        if(productDtoList == null){
            return Result.succ(200,"返回成功,暂无留言",null);
        }
        return Result.succ(200,"返回成功",productDtoList);
    }


}
