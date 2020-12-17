package cn.fleatransaction.service.impl;

import cn.fleatransaction.entity.DemandLabel;
import cn.fleatransaction.mapper.DemandLabelMapper;
import cn.fleatransaction.service.IDemandLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DemandLabelService extends ServiceImpl<DemandLabelMapper, DemandLabel> implements IDemandLabelService {
}
