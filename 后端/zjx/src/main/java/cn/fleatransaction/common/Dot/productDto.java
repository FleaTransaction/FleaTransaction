package cn.fleatransaction.common.Dot;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class productDto implements Serializable {
    private int productId;

    private int userId;

    private String productName;

    private float productPrice;

    private String productDescription;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp productTime;

    private String productPicture;

    private String productPhone;

    private String productQq;

    private String productWeChat;

    private int productCount;
}
