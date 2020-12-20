package cn.fleatransaction.common.Dot;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class loginByPhoneDot implements Serializable {

    @NotBlank(message = "手机账号不能为空")
    private String userphone;

    @NotBlank(message = "密码不能为空")
    private String password;
}
