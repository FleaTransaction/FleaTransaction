package cn.fleatransaction.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@TableName("label")
public class Label {

    @TableId(type = IdType.AUTO)
    private int labelId;

    @NotBlank(message = "标签不能为空")
    private String labelName;



}
