package cn.fleatransaction.service.impl;


import cn.fleatransaction.entity.UserFavourites;
import cn.fleatransaction.mapper.UserFavouritesMapper;
import cn.fleatransaction.service.IUserFavouritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserFavouritesService extends ServiceImpl<UserFavouritesMapper, UserFavourites> implements IUserFavouritesService {
}
