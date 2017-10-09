<template>
    <div class="login">
        <div class="login-bg1"></div>
        <div class="login-bg2"></div>
        <div class="row">            
            <div class="col-sm-4 col-sm-offset-4">
                <div class="login-content">
                    <h1>RSS</h1>
                    <form action="javascript:;" class="login-form" method="post">                        
                        <h3 class="form-title">账号登录</h3>
                        <div class="alert alert-danger" :class="{hide: !showAlert}">
                            <a class="close" data-dismiss="alert" @click="hideAlert">&times;</a>
                            <span>{{alertMessage}}</span>
                        </div>
                        <div class="row">
                            <div class="form-group col-sm-12">
                                <input v-model="user.email" class="form-control" type="text" autocomplete="off" placeholder="邮箱" name="email">
                            </div>
                            <div class="form-group col-sm-12">
                                <input @keyup="enterKey($event)" v-model="user.password" class="form-control" type="password" autocomplete="off" placeholder="密码（6-16位字符）" name="password">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                
                            </div>
                            <div class="col-sm-8 text-right">                                
                                <button class="btn" type="button" :class="{active: formValid}" :disabled="!formValid || formSubmit" @click="submitForm">登录</button>
                            </div>
                        </div>
                    </form>
                    <div class="row create-account"><div class="col-md-12"><router-link to="/register">没有账号，立即创建</router-link></div></div>    
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
                password: ''
            },
            showAlert: false,
            formSubmit: false,
            alertMessage: ''
        }
    },
    computed: {
        formValid: function() {
            const regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            const regPwd = /\w{6,16}/;
            return regEmail.test(this.user.email)&&regPwd.test(this.user.password);
        }
    },
    methods: {
        submitForm() {
            this.formSubmit = true;
            this.$http.post(config.apiHost+'/login', {email: this.user.email, password: this.user.password}).then(response => {

                var data = response.body;
                if(data.status!=1){
                    this.alertMessage = data.message;
                    this.showAlert = true;
                }else{
                    this.showAlert = false;
                    sessionStorage.setItem('user', JSON.stringify(data.data));
                    this.$router.push('/home')
                }
                this.formSubmit = false;
            
            }, response => {
                // error callback 
                this.alertMessage = "登录失败，请稍微再试！";
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
    .login
        position fixed
        width 100%
        height 100%        
        background-color rgb(54,65,80)
        display flex
        flex-direction column
        justify-content space-around
        .login-bg1
            position absolute
            top 0
            left 0
            width 100%
            height 100%
            background-image url(./15.jpg)
            background-repeat no-repeat
            background-size cover
            filter blur(5px)
        .login-bg2
            position absolute
            top 0
            left 0
            width 100%
            height 100%
            background-color rgba(54,65,80,0.5)
        .login-content            
            text-align center
            margin 0 20px
            flex 1
            h1
                margin-top -100px
                color #fff
            .login-form
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
                    
            .create-account
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

