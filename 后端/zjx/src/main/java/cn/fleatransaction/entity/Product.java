package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@TableName("product")
public class Product {

    @TableId(type = IdType.AUTO)
    private int productId;

    private int userId;

    @NotBlank(message = "商品名不能为空")
    private String productName;

    //@NotBlank(message = "商品价格不能为空")
    private Float productPrice;

    @NotBlank(message = "商品描述不能为空")
    private String productDescription;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp productTime;

    private String productPhone;

    private String productQq;

    private String productWeChat;

    private int productCount;

//    @Override
//    public int compareTo(Product ob) {
//        return this.productCount.compareTo(ob.getProductCount());
//    }
}
