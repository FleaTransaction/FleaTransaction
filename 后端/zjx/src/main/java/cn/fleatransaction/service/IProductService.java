package cn.fleatransaction.service;



import cn.fleatransaction.common.Dot.labelDto;
import cn.fleatransaction.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IProductService extends IService<Product> {


     List<String> getLabel();

     List<String> getChildLabel(String label_name);



}
