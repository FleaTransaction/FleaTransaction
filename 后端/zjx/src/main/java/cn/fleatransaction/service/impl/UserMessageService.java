package cn.fleatransaction.service.impl;

import cn.fleatransaction.entity.UserMessage;
import cn.fleatransaction.mapper.UserMessageMapper;
import cn.fleatransaction.service.IUserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserMessageService extends ServiceImpl<UserMessageMapper, UserMessage> implements IUserMessageService {
}
