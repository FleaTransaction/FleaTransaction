package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.Label;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.service.ILabelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="标签接口")
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    ILabelService labelService;

    @ApiOperation(value="查询标签")
    @GetMapping("/query")
    public Result queryLabel(String labelId){
        Label label=labelService.getOne(new QueryWrapper<Label>().eq("label_id",labelId));
        return Result.succ(200,"查询成功",label);
    }
    @ApiOperation(value="添加标签")
    @PostMapping("/add")
    public Result addLabel(@Validated @RequestBody Label label){
       labelService.save(label);
        return Result.succ(200,"添加成功",label);
    }
    @ApiOperation(value="删除标签")
    @GetMapping("/remove")
    public Result removeLabel(String labelId){
        labelService.removeById(labelId);
        return Result.succ(200,"删除成功",null);
    }
    @ApiOperation(value="修改标签")
    @PostMapping("/modify")
    public Result modifyLabel(@Validated @RequestBody Label label){
        labelService.updateById(label);
        return Result.succ(200,"修改成功",label);
    }
    @ApiOperation(value="返回所有标签")
    @GetMapping("/list")
    public Result listLabel(){
        List<Label> label=labelService.list();
        return Result.succ(200,"返回成功",label);
    }

}
