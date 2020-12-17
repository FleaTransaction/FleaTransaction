package cn.fleatransaction.service.impl;

import cn.fleatransaction.entity.ProductLabel;
import cn.fleatransaction.mapper.ProductLabelMapper;
import cn.fleatransaction.service.IProductLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductLabelService extends ServiceImpl<ProductLabelMapper, ProductLabel> implements IProductLabelService {
}
