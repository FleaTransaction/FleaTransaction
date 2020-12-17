package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName("product")
public class Product {

    @TableId(type = IdType.AUTO)
    private int productId;

    @TableId(type = IdType.AUTO)
    private int userId;

    @NotBlank(message = "商品名不能为空")
    private String productName;

    @NotBlank(message = "商品价格不能为空")
    private Float productPrice;

    @NotBlank(message = "商品描述不能为空")
    private String productDescription;

}
