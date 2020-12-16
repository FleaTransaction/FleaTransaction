package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@TableName("user")
public class User {
    @TableId(type= IdType.AUTO)
    private Integer userId;

    private String userName;

    @Email(message = "email格式不正确")
    private String userEmail;

    private String userPhone;

    private String password;

    private float userCredit;

    private String userAvator;

    private String realName;

    private String idCard;

    private String stuNumber;

}
