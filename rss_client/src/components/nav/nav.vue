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
                    <router-link to="/home" class="navbar-brand">RSS</router-link>
                </div>               
                <div class="collapse navbar-collapse" id="example-navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><router-link to="/review">读后感</router-link></li>
			            <li><router-link to="/sys_push">我的推送</router-link></li>
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
            user: JSON.parse(sessionStorage.getItem('user'))
        }
    },
    methods: {
        logout () {
            sessionStorage.removeItem('user');
            this.$router.push('login');
        }
    }
}
</script>

<style lang="stylus" scoped>
    .navbar-inverse
        background-color #2B3643
        border-color #2B3643
        .navbar-toggle
            border-color #ccc
            &:hover
            &:focus
                background-color #3F4F62
        .open
        .active
            a
                &:hover
                &:active
                &:visited
                    background-color #3F4F62
</style>

