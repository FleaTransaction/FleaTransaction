package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.ChildLabel;
import cn.fleatransaction.entity.Label;
import cn.fleatransaction.service.IChildLabelService;
import cn.fleatransaction.service.ILabelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="子标签接口")
@RestController
@RequestMapping("/childLabel")
public class ChildLabelController {

    @Autowired
    IChildLabelService childLabelService;

    @ApiOperation(value="查询子标签")
    @GetMapping("/query")
    public Result queryChildLabel(String childLabelId){
        ChildLabel childLabel=childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_id",childLabelId));
        return Result.succ(200,"查询成功",childLabel);
    }
    @ApiOperation(value="添加子标签")
    @PostMapping("/add")
    public Result addChildLabel(@Validated @RequestBody ChildLabel childLabel){
       childLabelService.save(childLabel);
        return Result.succ(200,"添加成功",childLabel);
    }
    @ApiOperation(value="删除子标签")
    @GetMapping("/remove")
    public Result removeChildLabel(Integer childLabelId){
        childLabelService.removeById(childLabelId);
        return Result.succ(200,"删除成功",null);
    }
    @ApiOperation(value="修改子标签")
    @PostMapping("/modify")
    public Result modifyChildLabel(@Validated @RequestBody ChildLabel childLabel){
        childLabelService.updateById(childLabel);
        return Result.succ(200,"修改成功",childLabel);
    }
    @ApiOperation(value="返回所有子标签")
    @GetMapping("/list")
    public Result listChildLabel(){
        List<ChildLabel> childLabelList=childLabelService.list();
        return Result.succ(200,"返回成功",childLabelList);
    }

}
