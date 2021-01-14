package cn.fleatransaction.service.impl;




import cn.fleatransaction.common.Dot.OneProductDto;
import cn.fleatransaction.common.Dot.labelDto;
import cn.fleatransaction.common.Dot.messageDto;
import cn.fleatransaction.common.Dot.productDto;
import cn.fleatransaction.entity.Product;
import cn.fleatransaction.mapper.ProductMapper;
import cn.fleatransaction.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
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

    @Override
    public java.util.List<productDto> getProductInfo() {
        return productMapper.getProductInfo();
    }

    @Override
    public List<productDto> queryProductInfo(String labelName,String ChildLabelName) {
        return productMapper.queryProductInfo(labelName,ChildLabelName);
    }

    @Override
    public OneProductDto getProductInfoById(int productId) {
        List<productDto> productDtos = productMapper.getProductInfoById(productId);
        OneProductDto t = new OneProductDto();
        t.setProductPicture(new ArrayList<>());
        for(productDto temp : productDtos){
            t.getProductPicture().add(temp.getProductPicture());
            t.setProductCount(temp.getProductCount());
            t.setProductDescription(temp.getProductDescription());
            t.setProductId(temp.getProductId());
            t.setUserId(temp.getUserId());
            t.setProductName(temp.getProductName());
            t.setProductPhone(temp.getProductPhone());
            t.setProductPrice(temp.getProductPrice());
            t.setProductQq(temp.getProductQq());
            t.setProductTime(temp.getProductTime());
            t.setProductWeChat(temp.getProductWeChat());
        }
        return t;
    }

    /*@Override
    public productDto getProductDto(int productId) {
        return productMapper.getProductDto(productId);
    }*/

    @Override
    public List<messageDto> getMessageById(int productId) {
        return productMapper.getMessageById(productId);
    }

    @Override
    public int saveProduct(Product product) {
        return productMapper.saveProduct(product);
    }


}
