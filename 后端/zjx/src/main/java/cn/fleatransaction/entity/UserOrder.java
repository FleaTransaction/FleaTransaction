package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_order")
public class UserOrder {

    @TableId(type = IdType.AUTO)
    private int orderId;

    private int userId;

    private int productId;

    private String orderStatus;
}
