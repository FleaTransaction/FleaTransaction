package cn.fleatransaction.mapper;



import cn.fleatransaction.common.Dot.labelDto;
import cn.fleatransaction.common.Dot.messageDto;
import cn.fleatransaction.common.Dot.productDto;
import cn.fleatransaction.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * @param id
     * @return
     */
    @Select("SELECT label_name FROM label")
    List<String> getLabel();

    @Select("SELECT child_label_name FROM child_label,label where child_label.label_id=label.label_id AND label_name=#{label_name}")
    List<String> getChildLabel(@Param("label_name") String label_name);

    @Select("select product.product_id,product_name,product_price,product_description,product_time,product_picture from product,product_pic where product.product_id=product_pic.product_id")
    List<productDto> getProductInfo();

    @Select("select product.product_id,product_name,product_price,product_description,product_time,product_picture,label_name,child_label_name from product,product_pic ,label,child_label,product_label\n" +
            "where product.product_id=product_pic.product_id\n" +
            "and product.product_id=product_label.product_id\n" +
            "and label.label_id=child_label.label_id\n" +
            "and product_label.child_label_id=child_label.child_label_id\n" +
            "and label_name=#{label_name} and child_label_name=#{child_label_name}")
    List<productDto> queryProductInfo(@Param("label_name")String labelName,@Param("child_label_name")String childLabelName);

    @Select("select product.product_id,product_name,product_price,product_description,product_time,product_picture from product,product_pic where product.product_id=product_pic.product_id and product.product_id=#{productId}")
    List<productDto> getProductInfoById(@Param("productId")int productId);

    @Select("select user_name,user_avatar,message,message_time from user_message,`user` where  `user`.user_id=user_message.user_id and user_message.product_id=#{productId}")
    List<messageDto> getMessageById(@Param("productId")int productId);

}
