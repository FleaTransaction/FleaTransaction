package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.ChildLabel;
import cn.fleatransaction.entity.DemandLabel;
import cn.fleatransaction.entity.Label;
import cn.fleatransaction.entity.ProDemand;
import cn.fleatransaction.service.IChildLabelService;
import cn.fleatransaction.service.IDemandLabelService;
import cn.fleatransaction.service.ILabelService;
import cn.fleatransaction.service.IProDemandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="需求标签接口")
@RestController
@RequestMapping("/demandLabel")
public class DemandLabelController {

    @Autowired
    IDemandLabelService demandLabelService;

    @Autowired
    IProDemandService proDemandService;

    @Autowired
    IChildLabelService childLabelService;

    @ApiOperation(value="查询需求标签")
    @GetMapping("/query")
    public Result queryDemandLabel(int demandId){
        DemandLabel demandLabel=demandLabelService.getOne(new QueryWrapper<DemandLabel>().eq("demand_id",demandId));
        if(demandLabel == null){
            return Result.succ(200,"查询成功，暂无该标签",null);
        }
        return Result.succ(200,"查询成功",demandLabel);
    }
    @ApiOperation(value="添加需求标签")
    @PostMapping("/add")
    @RequiresAuthentication
    @CrossOrigin
    public Result addDemandLabel(@Validated @RequestBody DemandLabel demandLabel){
        ProDemand proDemand=proDemandService.getOne(new QueryWrapper<ProDemand>().eq("demand_id",demandLabel.getDemandId()));
        if(proDemand==null)
            return Result.fail("该需求不存在，添加失败");
        ChildLabel childLabel=childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_id",demandLabel.getChildLabelId()));
        if(childLabel==null)
            return Result.fail("该标签不存在，添加失败");
       demandLabelService.save(demandLabel);
        return Result.succ(200,"添加成功",demandLabel);
    }
    @ApiOperation(value="删除需求标签")
    @GetMapping("/remove")
    @RequiresAuthentication
    @CrossOrigin
    public Result removeDemandLabel(@RequestBody DemandLabel demandLabel){
        ProDemand proDemand=proDemandService.getOne(new QueryWrapper<ProDemand>().eq("demand_id",demandLabel.getDemandId()));
        if(proDemand!=null)
            return Result.fail("该需求还存在，删除失败");
        demandLabelService.removeById(demandLabel.getDemandLabelId());
        return Result.succ(200,"删除成功",null);
    }

    @ApiOperation(value="修改需求标签")
    @PostMapping("/modify")
    @RequiresAuthentication
    @CrossOrigin
    public Result modifyDemandLabel(@Validated @RequestBody DemandLabel demandLabel){
        ChildLabel childLabel=childLabelService.getOne(new QueryWrapper<ChildLabel>().eq("child_label_id",demandLabel.getChildLabelId()));
        if(childLabel==null)
            return Result.fail("该标签不存在，修改失败");
        demandLabelService.updateById(demandLabel);
        return Result.succ(200,"修改成功",demandLabel);
    }

    @ApiOperation(value="返回所有需求标签")
    @GetMapping("/list")
    public Result listDemandLabel(){
        List<DemandLabel> demandLabelList=demandLabelService.list();
        if(demandLabelList == null){
            return Result.succ(200,"返回成功,暂无标签",null);
        }
        return Result.succ(200,"返回成功",demandLabelList);
    }

}
