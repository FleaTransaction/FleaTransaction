package cn.fleatransaction.service;



import cn.fleatransaction.common.Dot.OneProductDto;
import cn.fleatransaction.common.Dot.labelDto;
import cn.fleatransaction.common.Dot.messageDto;
import cn.fleatransaction.common.Dot.productDto;
import cn.fleatransaction.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductService extends IService<Product> {


     List<String> getLabel();

     List<String> getChildLabel(String label_name);

     List<productDto> getProductInfo();

     List<productDto> queryProductInfo(String labelName,String ChildLabelName);

     OneProductDto getProductInfoById(int productId);

     //productDto getProductDto(int productId);

     List<messageDto> getMessageById(int productId);

     int saveProduct(Product product);


}
