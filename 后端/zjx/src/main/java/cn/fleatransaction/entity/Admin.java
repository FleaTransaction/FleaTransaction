package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@TableName("admin")
public class Admin {
    @TableId(type= IdType.AUTO)
    private Integer adminId;

    @Email(message = "email格式不正确")
    private String adminEmail;

    private String adminPhone;

    private String password;

}
