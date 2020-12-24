package cn.fleatransaction.service.impl;




import cn.fleatransaction.common.Dot.labelDto;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.mapper.ProductMapper;
import cn.fleatransaction.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    ProductMapper productMapper;

    public List<String> getLabel(){
        return productMapper.getLabel();
    }

    @Override
    public java.util.List<String> getChildLabel(String label_name) {
        return productMapper.getChildLabel(label_name);
    }
}
