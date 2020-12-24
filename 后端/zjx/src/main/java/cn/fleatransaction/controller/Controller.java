package cn.fleatransaction.controller;

import cn.fleatransaction.common.Dot.labelDto;
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

@Api(tags="提供给前端的工具类")
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
        return Result.succ(200,"返回成功",productDtoList);
    }
    @ApiOperation(value="返回指定标签的商品信息")
    @GetMapping("/queryProduct")
    Result queryProductInfo(String labelName,String childLabelName){
        List<productDto> productDtoList=productService.queryProductInfo(labelName,childLabelName);
        return Result.succ(200,"返回成功",productDtoList);
    }

}
