package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.ChildLabel;
import cn.fleatransaction.entity.Label;
import cn.fleatransaction.service.IChildLabelService;
import cn.fleatransaction.service.ILabelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
    public Result queryChildLabel(@RequestParam("childlabelid") int childLabelId){
        ChildLabel childLabel=childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_id",childLabelId));
        if(childLabel != null) {
            return Result.succ(200, "查询成功", childLabel);
        }else{
            return Result.succ(200, "查询成功，暂无子标签", null);
        }
    }
    @ApiOperation(value="添加子标签")
    @PostMapping("/add")
    @RequiresAuthentication
    @CrossOrigin
    public Result addChildLabel(@Validated @RequestBody ChildLabel childLabel){
       if(childLabelService.save(childLabel)) {
           return Result.succ(200, "添加成功", childLabel);
       }
       return Result.fail("添加失败！请稍后再试");
    }
    @ApiOperation(value="删除子标签")
    @GetMapping("/remove")
    @RequiresAuthentication
    @CrossOrigin
    public Result removeChildLabel(Integer childLabelId){
        if(childLabelService.removeById(childLabelId)){
            return Result.succ(200, "删除成功", null);
        }
        return Result.fail("删除失败！请稍后再试");
    }
    @ApiOperation(value="修改子标签")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modifyChildLabel(@Validated @RequestBody ChildLabel childLabel){
        if(childLabelService.updateById(childLabel)) {
            return Result.succ(200, "修改成功", childLabel);
        }
        return Result.fail("修改失败！请稍后再试");
    }
    @ApiOperation(value="返回所有子标签")
    @GetMapping("/list")
    public Result listChildLabel(){
        List<ChildLabel> childLabelList=childLabelService.list();
        if(childLabelList != null) {
            return Result.succ(200, "返回成功", childLabelList);
        }else{
            return Result.succ(200, "返回成功,暂无子标签", null);
        }
    }

}
