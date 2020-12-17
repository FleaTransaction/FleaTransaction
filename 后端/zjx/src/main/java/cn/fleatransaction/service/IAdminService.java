package cn.fleatransaction.service;


import cn.fleatransaction.entity.Admin;
import cn.fleatransaction.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAdminService extends IService<Admin> {


    User findUser (Integer id); //通过id找用户
    User queryUserName(String name); //通过姓名查找用户
    User registerUser(String phone , String password,String useremail); //通过手机号注册用户
}
