package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("demand_label")
public class DemandLabel {

    @TableId(value = "demandLabelId", type = IdType.AUTO)
    private int demandLabelId;

    private int childLabelId;

    private int demandId;


}
