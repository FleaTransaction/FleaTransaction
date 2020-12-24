package cn.fleatransaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_label")
public class ProductLabel {

    @TableId(value = "productLabelId", type = IdType.AUTO)
    private int productLabelId;

    private int productId;

    private int childLabelId;

}
