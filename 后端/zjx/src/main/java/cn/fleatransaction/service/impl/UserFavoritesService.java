package cn.fleatransaction.service.impl;


import cn.fleatransaction.entity.UserFavorites;
import cn.fleatransaction.mapper.UserFavoritesMapper;
import cn.fleatransaction.service.IUserFavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserFavoritesService extends ServiceImpl<UserFavoritesMapper, UserFavorites> implements IUserFavoritesService {
}
