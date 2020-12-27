package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_pic")
public class ProductPic {

    private int productId;

    private String productPicture;

    @TableId(type = IdType.AUTO)
    private int pictureId;

}
