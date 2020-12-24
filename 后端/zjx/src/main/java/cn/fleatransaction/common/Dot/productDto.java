package cn.fleatransaction.common.Dot;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class productDto implements Serializable {
    private String productName;

    private double productPrice;

    private String productDescription;

    private Timestamp productTime;

    private String productPicture;
}
