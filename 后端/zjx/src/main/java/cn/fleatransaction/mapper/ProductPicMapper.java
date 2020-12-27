package cn.fleatransaction.mapper;


import cn.fleatransaction.entity.ProductPic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface ProductPicMapper extends BaseMapper<ProductPic> {

   // @Update("update product_pic set product_picture = ? where product_id = ?")
}
