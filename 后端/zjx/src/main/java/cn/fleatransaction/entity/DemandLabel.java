package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("demand_label")
public class DemandLabel {

    private int demandId;

    private int labelId;


}
