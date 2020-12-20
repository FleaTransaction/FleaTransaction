package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_pic")
public class ProductPic {

    private int productId;

    private String productPicture;

}
