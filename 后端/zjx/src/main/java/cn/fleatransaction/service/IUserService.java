package cn.fleatransaction.service;


import cn.fleatransaction.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {


    User findUser (Integer id); //通过id找用户
    User queryUserName(String name); //通过姓名查找用户
    Integer registerUserByPhone(String phone , String password); //通过手机号注册用户
    Integer registerUserByEmail(String email , String password);//通过电子邮件注册
}
