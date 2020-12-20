package cn.fleatransaction.service.impl;

import cn.fleatransaction.entity.ProDemand;
import cn.fleatransaction.mapper.ProDemandMapper;
import cn.fleatransaction.service.IProDemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProDemandService extends ServiceImpl<ProDemandMapper, ProDemand> implements IProDemandService {
}
