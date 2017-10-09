<template>
    <div class="register">
        <div class="register-bg1"></div>
        <div class="register-bg2"></div>
        <div class="row">            
            <div class="col-md-4 col-md-offset-4">
                <div class="register-content">
                    <h1>RSS</h1>
                    <form action="javascript:;" class="register-form" method="post">                        
                        <h3 class="form-title">新建账号</h3>
                        <div class="alert alert-danger" :class="{hide: !showAlert}">
                            <a class="close" data-dismiss="alert" @click="hideAlert">&times;</a>
                            <span>{{alertMessage}}</span>
                        </div>
                        <div class="alert alert-success" :class="{hide: !redirecting}">
                            <span>注册成功，正在跳转到登录页面......</span>
                        </div>
                        <div class="alert alert-danger" :class="{hide: re_password==='' || re_password === user.password}">
                            <span>两次输入的密码不一致！</span>
                        </div>
                        <div class="row">
                            <div class="form-group col-sm-12">
                                <input v-model="user.email" class="form-control" type="text" autocomplete="off" placeholder="邮箱" name="email">
                            </div>
                            <div class="form-group col-sm-12">
                                <input v-model="user.nickname" class="form-control" type="text" autocomplete="off" placeholder="昵称" name="nickname">
                            </div>
                            <div class="form-group col-sm-12">
                                <input v-model="user.password" class="form-control" type="password" autocomplete="off" placeholder="密码（6-16位字符）" name="password">
                            </div>
                            <div class="form-group col-sm-12">
                                <input @keyup="enterKey($event)" v-model="re_password" class="form-control" type="password" autocomplete="off" placeholder="确认密码" name="password">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                
                            </div>
                            <div class="col-sm-8 text-right">                                
                                <button class="btn" type="button" :class="{active: formValid}" :disabled="!formValid || formSubmit" @click="submitForm">提交</button>
                            </div>
                        </div>
                    </form>
                    <div class="row login"><div class="col-md-12"><router-link to="/login">已有账号，现在登录</router-link></div></div>    
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import config from '@/config';
export default {
  data () {
        return {
            user: {
                email: '',
                nickname: '',
                password: ''
            },
            re_password: '',
            showAlert: false,
            formSubmit: false,
            redirecting: false,
            alertMessage: ''
        }
    },
    computed: {
        formValid: function() {
            const regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            const regPwd = /\w{6,16}/;
            return regEmail.test(this.user.email)&&this.user.nickname&&regPwd.test(this.user.password)&&this.user.password==this.re_password;
        }
    },
    methods: {
        submitForm() {
            this.formSubmit = true;
            this.$http.post(config.apiHost+'/addUser',{email: this.user.email, nickname: this.user.nickname, password: this.user.password}).then(response => {

                var data = response.body;
                if(data.status!=1){
                    this.alertMessage = data.message;
                    this.showAlert = true;
                }else{
                    this.showAlert = false;
                    this.redirecting = true;
                    var router = this.$router;
                    setTimeout(function(){
                        router.push('/login');
                    },1000);
                }
                this.formSubmit = false;
            
            }, response => {
                // error callback 
                this.alertMessage = "登录失败，请稍微重试！";
                this.formSubmit = false;
            });
        },
        hideAlert() {
            this.showAlert = false;
        },
        enterKey (event) {
            if(event.keyCode==13 && this.formValid && !this.formSubmit){
              this.submitForm();
            }
        }
    }
}
</script>

<style lang="stylus" scoped>
    .register
        position fixed        
        width 100%
        height 100%        
        background-color rgb(54,65,80)
        display flex
        flex-direction column
        justify-content space-around
        .register-bg1
            z-index -99
            position absolute
            top 0
            left 0
            width 100%
            height 100%
            background-image url(./30.jpg)
            background-repeat no-repeat
            background-size cover
            filter blur(5px)
        .register-bg2
            position absolute
            top 0
            left 0
            width 100%
            height 100%
            background-color rgba(54,65,80,0.5)
        .register-content            
            text-align center
            margin 0 20px
            flex 1
            h1
                margin-top -50px
                color #fff
            .register-form
                border-radius 5px 5px 0 0
                color #333                
                margin-top 50px
                background-color #fff
                padding 20px
                .form-title
                    color #32c5d2
                    margin-bottom 30px
                .btn
                    outline none         
                    color #999
                .btn.active
                    color #fff
                    background-color #32c5d2
                    
            .login
                padding 0 15px
                .col-md-12
                    background-color #6c7a8d  
                    border-radius 0 0 5px 5px
                    height 50px
                    color #c3cedd
                    font-size 14px
                    font-weight 600
                    text-align center
                    line-height 50px        
                    a
                        text-decoration none
                        color #c3cedd
                        display inline-block
                        width 100%
</style>

