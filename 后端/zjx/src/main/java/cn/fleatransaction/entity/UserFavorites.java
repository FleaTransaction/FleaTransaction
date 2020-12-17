package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_favorites")
public class UserFavorites {

    private int userId;


    private int productId;

}
