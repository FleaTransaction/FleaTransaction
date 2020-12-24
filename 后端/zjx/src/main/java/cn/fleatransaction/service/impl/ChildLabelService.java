package cn.fleatransaction.service.impl;




import cn.fleatransaction.entity.ChildLabel;
import cn.fleatransaction.entity.Label;
import cn.fleatransaction.mapper.ChildLabelMapper;
import cn.fleatransaction.mapper.LabelMapper;
import cn.fleatransaction.service.IChildLabelService;
import cn.fleatransaction.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ChildLabelService extends ServiceImpl<ChildLabelMapper, ChildLabel> implements IChildLabelService {

}
