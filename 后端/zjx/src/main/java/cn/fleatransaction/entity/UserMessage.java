package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("user_message")
public class UserMessage {

    private int userId;

    private int productId;

    private String message;

    @TableId(type = IdType.AUTO)
    private int messageId;

    private Timestamp messageTime;

}
