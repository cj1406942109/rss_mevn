<template>
  <div class="my-nav">
      <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#example-navbar-collapse">
                        <span class="sr-only">切换导航</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand">RSS</a>
                </div>               
                <div class="collapse navbar-collapse" id="example-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li v-for="nav in navList" :key="nav.id"><router-link :to="nav.href">{{nav.title}}</router-link></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                {{user.nickname}}
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a @click="logout"><span class="glyphicon glyphicon-log-out"></span>退出登录</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
  </div>
</template>

<script>
export default {
    data () {
        return {
            navList: [{
                title:'读后感',
                href:'/home/review_list',
            },{
                title:'我的推送',
                href:'/home/sys_push',
            }],
            user: ''
        }
    },
    created () {
        this.user = JSON.parse(sessionStorage.getItem('user'));
    },
    methods: {
        logout () {
            sessionStorage.removeItem('user');
            this.$router.push('/login');
        }
    }
}
</script>

<style lang="stylus" scoped>    
    .navbar-inverse    
        background-color #2B3643
        border-color #2B3643
        .navbar-brand
            color #ffffff
        .navbar-toggle
            border-color #ccc
            &:hover
            &:focus
                background-color #3F4F62
        .open
        .active
            a
                cursor pointer
                &:hover
                &:active
                &:visited
                    background-color #3F4F62
                    color #fff
</style>

