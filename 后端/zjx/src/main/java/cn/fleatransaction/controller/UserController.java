package cn.fleatransaction.controller;





import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.service.IUserService;
import cn.fleatransaction.util.UploadUtils;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    UploadUtils uploadUtils;


    @ApiOperation(value = "查询用户")
    @GetMapping("/query")
    public Result queryStudent(Integer id){
        User oneuser = userService.findUser(id);
        return Result.succ(200,"查询成功",oneuser);
    }


    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public Result queryAllUser(){
        List<User> userList=userService.list();
        return Result.succ(200,"获取成功",userList);
    }


    @ApiOperation(value = "获取用户数量")
    @GetMapping("/count")
    public Result count(){
        int count=userService.count();
        return Result.succ(200,"获取成功",count);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/modify")
    public Result save(@Validated @RequestBody User user){

        userService.save(user);
        return Result.succ(200,"修改成功",user);
    }

    @PostMapping("/uploadavatar")
    @ApiOperation(value = "头像上传")
    public Result upload(@RequestParam("imgfile") MultipartFile imgfile, int userid){

        if (imgfile.isEmpty()) {
            return Result.fail("上传文件不能为空！");
        }
        String filename = imgfile.getOriginalFilename();
        String prefix = filename.substring(filename.lastIndexOf(".") + 1);
        filename = UUID.randomUUID().toString().replace("-", "") + "." + prefix;

        // 存放上传图片的文件夹
        File fileDir = uploadUtils.getAvatarDirFile();
        // 输出文件夹绝对路径  -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径
        String url = fileDir.getAbsolutePath();
        try {
            // 构建真实的文件路径
            File newFile = new File(url + File.separator + filename);
            System.err.println(newFile.getAbsolutePath());
            String urlava = newFile.getAbsolutePath();
            // 上传图片到 -》 “绝对路径”
            imgfile.transferTo(newFile);
            User u = new User();
            //u.setUserId(uid);
            u.setUserAvatar(urlava);
            //userService.updateById(u);
            /**
             * 保存头像
             */
            userService.update(u, new QueryWrapper<User>().eq("user_id", userid));
            User user = userService.getOne(new QueryWrapper<User>().eq("user_id", userid));
            return Result.succ(200,"上传成功",MapUtil.builder()
                    .put("userid",user.getUserId())
                    .put("username",user.getUserName())
                    .put("userphone",user.getUserPhone())
                    .put("userEmail",user.getUserEmail())
                    .put("useravator",user.getUserAvatar())
                    .put("usercredit",user.getUserCredit())
                    .map());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("上传异常: "+ e.getMessage());
        }
    }



}
