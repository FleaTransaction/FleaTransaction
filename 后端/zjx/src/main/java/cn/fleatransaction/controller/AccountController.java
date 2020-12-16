package cn.fleatransaction.controller;


import cn.fleatransaction.common.Dot.loginByPhoneDot;
import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.service.IUserService;
import cn.fleatransaction.util.JwtUtils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/login")
public class AccountController {

    @Autowired
    IUserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @CrossOrigin
    @PostMapping("/phone")
    public Result loginByPhone(@Validated @RequestBody loginByPhoneDot loginByPhoneDot, HttpServletResponse httpServletResponse){
        User user = userService.getOne(new QueryWrapper<User>().eq("user_phone",loginByPhoneDot.getUserphone()));
        Assert.notNull(user,"用户不存在");
        if(!user.getPassword().equals(loginByPhoneDot.getPassword())){
            return Result.fail("密码错误");
        }
        String jwt = jwtUtils.generateToken(user.getUserId());
        httpServletResponse.setHeader("Authorization",jwt);
        httpServletResponse.setHeader("Access-Control-Expose-Headers","Authorization");


        return Result.succ(MapUtil.builder()
                .put("userid",user.getUserId())
                .put("username",user.getUserName())
                .put("userphone",user.getUserPhone())
                .put("userEmail",user.getUserEmail())
                .put("useravator",user.getUserAvator())
                .put("usercredit",user.getUserCredit())
                .map()
        );
    }
}
