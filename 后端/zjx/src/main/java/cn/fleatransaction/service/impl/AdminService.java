package cn.fleatransaction.service.impl;



import cn.fleatransaction.entity.Admin;
import cn.fleatransaction.entity.User;
import cn.fleatransaction.mapper.AdminMapper;
import cn.fleatransaction.mapper.UserMapper;
import cn.fleatransaction.service.IAdminService;
import cn.fleatransaction.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    AdminMapper adminMapper;


    @Override
    public User findUser(Integer id) {
        return null;
    }

    @Override
    public User queryUserName(String name) {
        return null;
    }

    @Override
    public User registerUser(String phone, String password, String useremail) {
        return null;
    }


}
