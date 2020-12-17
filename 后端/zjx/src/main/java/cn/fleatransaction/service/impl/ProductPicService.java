package cn.fleatransaction.service.impl;

import cn.fleatransaction.entity.ProductPic;
import cn.fleatransaction.mapper.ProductPicMapper;
import cn.fleatransaction.service.IProductPicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductPicService extends ServiceImpl<ProductPicMapper, ProductPic> implements IProductPicService {
}
