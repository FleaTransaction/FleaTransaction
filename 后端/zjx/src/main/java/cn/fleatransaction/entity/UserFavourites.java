package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_favourites")
public class UserFavourites {

    private int userId;

    @TableId(value = "favouritesId", type = IdType.AUTO)
    private int favouritesId;

    private int productId;

}
