package cn.fleatransaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("child_label")
public class ChildLabel {
    @TableId(value = "childLabelId", type = IdType.AUTO)
    private int childLabelId;

    private  int labelId;

    private String childLabelName;
}
