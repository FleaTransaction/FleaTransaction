<template>
    <div class="container">
        <div class="login_box">
            <!-- 登录文字 -->
            <h3 class='words'>手机号登录</h3>
             <!-- 登录表单 -->
            <el-form :model="loginForm" :rules='loginFormRules' ref="loginFormRef" class="login_form">
                <!-- 用户名 -->
                <el-form-item prop="userphone" class="s">
                    <el-input  v-model="loginForm.userphone" prefix-icon="el-icon-phone" placeholder="手机号"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password" class="s">
                    <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" type="password" placeholder="密码"></el-input>
                </el-form-item>
                <!-- 邮箱登录 -->
                <router-link to='EmailLogin' class="goEmailLogin">邮箱登录 |</router-link>
                <!-- 去注册 -->
                <router-link to='Register' class="goRegister">去注册</router-link>
                <!-- 登录按钮 -->
                <el-form-item class="btns">
                    <el-button type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            loginForm:{
                userphone:'',
                password:''
            },
            loginFormRules:{
                userphone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            }
    
        }
    },
    methods:{
        login(){
            this.$refs.loginFormRef.validate(async valid =>{
                if(!valid) return;
               
                const {data: res}=await this.$http.post("login/phone",this.loginForm);
                console.log(res);
                 if(res.code === 200){
                     window.sessionStorage.setItem('token',res.data.token);
                    this.$router.push("/home");
                }
                else if(res.code === 201){
                    window.sessionStorage.setItem('token',res.data.token);
                    this.$router.push("/admin");
                }
                else if(res.code === 400){
                     return this.$message.error((res.msg));
                }
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
    .login_box{
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
    .goEmailLogin{
        position: relative;
        float: left;
        left: 235px;
        text-decoration: none;
        font-family: "微软雅黑";
        color: black;
    }
    .goRegister{
        position: relative;
        left :240px;
        text-decoration: none;
        margin-bottom: 30px;
        font-family: "微软雅黑";
        color: black;
    }
</style>