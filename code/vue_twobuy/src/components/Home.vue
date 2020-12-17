<template>
    <div id="container">
    <div id="nav" class="shadow">
        <el-menu :default-active="activeIndex" mode="horizontal">
        <el-menu-item><img src="../assets/logo.png" alt="" class="img"></el-menu-item>
        <el-menu-item index="1" style="margin-left:100px">首页</el-menu-item>
        <el-menu-item index="2">求购</el-menu-item>
        <el-menu-item index="3">发布求购</el-menu-item>
        <el-menu-item index="4" style="margin-right:800px">发布商品</el-menu-item>
        <router-link to='#' class="menu_word">个人中心</router-link>
        <img src="../assets/user.png" class="menu_user_img">
        </el-menu>
    </div>
    <!-- 分类容器 -->
    <div id="sortContainer">
        <!-- 分类菜单 -->
        <div id="sortMenu">
            <el-tabs type="border-card" tab-position="left">
                <el-tab-pane label="数码产品" class="s">数码</el-tab-pane>
                <el-tab-pane label="书籍教材">配置管理</el-tab-pane>
                <el-tab-pane label="衣服鞋子">角色管理</el-tab-pane>
                <el-tab-pane label="体育健身">定时任务补偿</el-tab-pane>
                <el-tab-pane label="虚拟产品">定时任务补偿</el-tab-pane>
                <el-tab-pane label="生活用品">定时任务补偿</el-tab-pane>
            </el-tabs>
        </div>
    </div>
    </div>
</template>

<style scoped>
    .shadow{
        border-bottom: 30px;
        box-shadow: 0px 0px 20px 0px rgba(175, 187, 204, 0.2);
        
    }
    .img{
        height: 58px;
        widows: 58px;
    }
    .menu_word{
        position: relative;
        float: left;
        text-decoration: none;
        font-size: 14px;
        top:20px;
        padding: 0px 10px;
        color:  #909399
    }
    .menu_user_img{
        position: relative;
        top: 10px;
    }
    #container{
        background-color: #F2F4F7;  
        height: 100%;
        border-top: 1px solid #F2F4F7;
        box-sizing: border-box;
    }
    #sortContainer{
        position: absolute;
        background-color: #fff;
        box-shadow: 0px 0px 20px 0px rgba(175, 187, 204, 0.2);
        width: 1000px;
        height: 450px;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
    }
    .s{
        position: relative;
        margin-bottom: 30px;
    } div /deep/ .el-tabs__content{   
        width: 200px;
    }
</style>>
