package cn.fleatransaction.controller;

import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.ChildLabel;
import cn.fleatransaction.entity.ProDemand;
import cn.fleatransaction.service.IChildLabelService;
import cn.fleatransaction.service.IProDemandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="需求接口")
@RestController
@RequestMapping("/proDemand")
public class ProDemandController {

    @Autowired
    IProDemandService proDemandService;

    @ApiOperation(value="查询需求")
    @GetMapping("/query")
    public Result queryProDemand(String demandId){
        ProDemand proDemand=proDemandService.getOne(new QueryWrapper<ProDemand>().eq("demand_id",demandId));
        return Result.succ(200,"查询成功",proDemand);
    }
    @ApiOperation(value="根据产品名查询")
    @GetMapping("/queryDemand")
    public Result queryProDemandByName(String demandName){
        List<ProDemand> proDemand=proDemandService.list(new QueryWrapper<ProDemand>().eq("demand_Name",demandName));
        return Result.succ(200,"查询成功",proDemand);
    }
    @ApiOperation(value="添加需求")
    @PostMapping("/add")
    public Result addProDemand(@Validated @RequestBody ProDemand proDemand){
       proDemandService.save(proDemand);
        return Result.succ(200,"添加成功",proDemand);
    }
    @ApiOperation(value="删除需求")
    @GetMapping("/remove")
    public Result removeProDemand(Integer demandId){
        proDemandService.removeById(demandId);
        return Result.succ(200,"删除成功",null);
    }
    @ApiOperation(value="修改需求")
    @PostMapping("/modify")
    public Result modifyProDemand(@Validated @RequestBody ProDemand proDemand){
        proDemandService.updateById(proDemand);
        return Result.succ(200,"修改成功",proDemand);
    }
    @ApiOperation(value="返回所有需求")
    @GetMapping("/list")
    public Result listProDemand(){
        List<ProDemand> proDemandList=proDemandService.list();
        return Result.succ(200,"返回成功",proDemandList);
    }

}
