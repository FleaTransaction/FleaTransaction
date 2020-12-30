package cn.fleatransaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@TableName("pro_demand")
public class ProDemand {

    @TableId(type = IdType.AUTO)
    private int demandId;

    private int userId;

    @NotBlank(message = "需求商品名不能为空")
    private String demandName;

    @NotBlank(message = "需求描述不能为空")
    private String demandDescription;

    @NotBlank(message = "需求价格不能为空")
    private double demandPrice;

    private Timestamp demandTime;

    private String demandPhone;

    private String demandWeChat;

    private String demandQq;
}
