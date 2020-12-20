package cn.fleatransaction.service.impl;

import cn.fleatransaction.entity.UserOrder;
import cn.fleatransaction.mapper.UserOrderMapper;
import cn.fleatransaction.service.IUserOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserOrderService extends ServiceImpl<UserOrderMapper, UserOrder> implements IUserOrderService {
}
