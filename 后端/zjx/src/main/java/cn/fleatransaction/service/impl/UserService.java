package cn.fleatransaction.service.impl;



import cn.fleatransaction.entity.User;
import cn.fleatransaction.mapper.UserMapper;
import cn.fleatransaction.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    public User findUser (Integer id){
        return userMapper.findUser(id);
    }

    @Override
    public User queryUserName(String name) {
        return userMapper.queryUserName(name);
    }

    @Override
    public User registerUser(String phone, String password,String useremail) {
        User user = new User();
        user.setUserPhone(phone);
        user.setPassword(password);
        user.setUserEmail(useremail);
        userMapper.insertUser(user);
        return user;
    }
}
