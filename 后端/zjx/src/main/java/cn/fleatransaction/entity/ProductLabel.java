package cn.fleatransaction.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_label")
public class ProductLabel {

    private int productId;

    private int labelId;

}
