package cn.fleatransaction.common.Dot;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {
    @NotBlank(message = "邮箱账号不能为空")
    @Email(message = "邮箱格式不正确")
    private String useremail;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "手机号不能为空")
    private String userphone;
}
