<template>
    <div class="container">
        <div class="register_box">
            <!-- 注册文字 -->
            <h3 class='words'>注册</h3>
             <!-- 注册表单 -->
            <el-form :model="registerForm" :rules='registerFormRules' ref="registerFormRef" class="register_form">
                <!-- 手机号 -->
                <el-form-item prop="userphone" class="s">
                    <el-input  v-model="registerForm.userphone" prefix-icon="el-icon-phone" placeholder="手机号"></el-input>
                </el-form-item>
                <!-- 邮箱 -->
                <el-form-item prop="useremail" class="s">
                    <el-input  v-model="registerForm.useremail" prefix-icon="el-icon-message" placeholder="邮箱"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password" class="s">
                    <el-input v-model="registerForm.password" prefix-icon="el-icon-lock" type="password" placeholder="密码"></el-input>
                </el-form-item>
                <!-- 确认密码 -->
                <el-form-item prop="password_again" class="s">
                    <el-input v-model="registerForm.password_again" prefix-icon="el-icon-lock" type="password" placeholder="确认密码"></el-input>
                </el-form-item> 
                <!-- 去登录 -->
                <router-link to='PhoneLogin' class="goLogin">去登录</router-link>
                <!-- 注册按钮 -->
                <el-form-item class="btns">
                    <el-button type="primary" @click="register">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        var checkPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.registerForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      var checkPhone =(rule,value,callback)=>{ 
          if(value==='')    {callback(new Error('请输入手机号'))}
          else if(!(/^1\d{10}$/.test(value))){ 
                callback(new Error("手机号码有误，请重填"));}
            else{
                callback();
            }
        }
        var checkEmail =(rule,value,callback) =>{
            if(value==='') { callback(new Error('请输入邮箱'))}
            else if(!(/^\w+@[a-z0-9]+\.[a-z]{2,4}$/).test(value))
                callback(new Error("邮箱输入有误，请重填"))
            else  callback();
        }
        return{
            registerForm:{
                userphone:'19967308559',
                useremail:'793679738@qq.com',
                password:'12345678',
                password_again:''
            },
            registerFormRules:{
                userphone: [{ validator: checkPhone,trigger:'blur' }],
                useremail: [{ validator: checkEmail,trigger:'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' },
                         { min: 8, max: 16, message: '长度在 8 到 16 个字符', trigger: 'blur' }],
                password_again: [{validator: checkPassword, trigger: 'blur'}]
            }
    
        }
    },
    methods:{ 
        register(){
       this.$refs.registerFormRef.validate(async valid =>{
                      if(!valid) return;
                     
                      const {data: res}=await this.$http.post("login/register",this.registerForm);
                      console.log(res);
                       if(res.code !== 200) return this.$message.error((res.msg));
                       this.$message.success("注册成功！");
                          window.sessionStorage.setItem('token',res.data.token);
                          this.$router.push("/home");
                  });
}
    }
}
</script>

<style lang="less" scoped>
    .container{
        background-color: #F2F4F7;  
        height: 100%;
        border-top: 1px solid #F2F4F7;
        box-sizing: border-box;
    }
    .register_box{
        width: 360px;
        background-color: #fff;
        border-radius: 16px;
        box-shadow: 0px 0px 20px 0px rgba(175, 187, 204, 0.2);
        position: absolute;
        left: 50%;   
        top: 50%;
        transform: translate(-50%,-50%);
        padding: 70px 90px 50px;
    }

    .btns{
        margin-top: 25px;
        margin-left: 290px;
    }
    .words{
        color: #181E33;
        font-size: 20px;
        margin-bottom: 44px;
    }
    .s{   
        position: relative;
        margin-bottom: 30px;
    }
    /deep/ .el-input__inner{   
        border-radius: 24px;
        height: 48px;
        width: 360px;
    }
    .goLogin{
        text-decoration: none;
        font-family: "微软雅黑";
        margin-left: 310px;
        color: black;
    }
</style>