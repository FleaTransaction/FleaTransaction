package cn.fleatransaction.service.impl;




import cn.fleatransaction.entity.Product;
import cn.fleatransaction.mapper.ProductMapper;
import cn.fleatransaction.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> implements IProductService {


}
