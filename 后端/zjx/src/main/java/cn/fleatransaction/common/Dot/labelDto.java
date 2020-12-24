package cn.fleatransaction.common.Dot;

import lombok.Data;

import java.util.List;

@Data
public class labelDto {
    private String labelName;

    private List<String> childLabelName;
}
