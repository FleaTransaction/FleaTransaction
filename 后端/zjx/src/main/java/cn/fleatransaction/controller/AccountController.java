package cn.fleatransaction.controller;



import cn.fleatransaction.common.Dot.RegisterDto;
import cn.fleatransaction.common.Dot.loginByEmailDto;
import cn.fleatransaction.common.Dot.loginByPhoneDto;
import cn.fleatransaction.common.lang.Result;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.service.IUserService;
import cn.fleatransaction.util.JwtUtils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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

    @ApiOperation(value = "通过手机号登陆")
    @CrossOrigin
    @PostMapping("/phone")
    public Result loginByPhone(@Validated @RequestBody loginByPhoneDto loginByPhoneDto, HttpServletResponse httpServletResponse){

        User user = userService.getOne(new QueryWrapper<User>().eq("user_phone", loginByPhoneDto.getUserphone()));
        //Assert.notNull(user,"用户不存在");
        if(user == null){
            return Result.fail("用户不存在");
        }
        if(!user.getPassword().equals(loginByPhoneDto.getPassword())){
            return Result.fail("密码错误");
        }

        if(user.getUserStatus() == 1){
            return Result.succ(201,"管理员账户",user);
        }

        String jwt = jwtUtils.generateToken(user.getUserId());
        httpServletResponse.setHeader("Authorization",jwt);
        httpServletResponse.setHeader("Access-Control-Expose-Headers","Authorization");


        return Result.succ(200,"登录成功",MapUtil.builder()
                .put("userid",user.getUserId())
                .put("username",user.getUserName())
                .put("userphone",user.getUserPhone())
                .put("userEmail",user.getUserEmail())
                .put("useravator",user.getUserAvator())
                .put("usercredit",user.getUserCredit())
                .map()
        );
    }

    @ApiOperation(value = "通过邮箱登陆")
    @CrossOrigin
    @PostMapping("/email")
    public Result loginByEmail(@Validated @RequestBody loginByEmailDto loginByEmailDto, HttpServletResponse httpServletResponse){

        User user = userService.getOne(new QueryWrapper<User>().eq("user_email", loginByEmailDto.getUseremail()));
        //Assert.notNull(user,"用户不存在");
        if(user == null){
            return Result.fail("用户不存在");
        }
        if(!user.getPassword().equals(loginByEmailDto.getPassword())){
            return Result.fail("密码错误");
        }

        if(user.getUserStatus() == 1){
            return Result.succ(201,"管理员账户",user);
        }

        String jwt = jwtUtils.generateToken(user.getUserId());
        httpServletResponse.setHeader("Authorization",jwt);
        httpServletResponse.setHeader("Access-Control-Expose-Headers","Authorization");


        return Result.succ(200,"登录成功",MapUtil.builder()
                .put("userid",user.getUserId())
                .put("username",user.getUserName())
                .put("userphone",user.getUserPhone())
                .put("userEmail",user.getUserEmail())
                .put("useravator",user.getUserAvator())
                .put("usercredit",user.getUserCredit())
                .map()
        );
    }


    @ApiOperation(value = "注册")
    @CrossOrigin
    @PostMapping("/register")
    public Result Register(@Validated @RequestBody RegisterDto registerDto, HttpServletResponse httpServletResponse){
        User user = userService.getOne(new QueryWrapper<User>().eq("user_phone", registerDto.getUserphone()));
        if(user!=null)
            return Result.fail("手机号已被注册");
        User user1 = userService.getOne(new QueryWrapper<User>().eq("user_email", registerDto.getUseremail()));
        if(user1!=null)
            return Result.fail("邮箱已被注册");
        User user2=userService.registerUser(registerDto.getUserphone(),registerDto.getPassword(), registerDto.getUseremail());

        user = userService.getOne(new QueryWrapper<User>().eq("user_phone", registerDto.getUserphone()));

        String jwt = jwtUtils.generateToken(user.getUserId());
        httpServletResponse.setHeader("Authorization",jwt);
        httpServletResponse.setHeader("Access-Control-Expose-Headers","Authorization");


        return Result.succ(MapUtil.builder()
                .put("userid",user.getUserId())
                .put("userphone",user.getUserPhone())
                .put("userEmail",user.getUserEmail())
                .map()
        );
    }


    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
