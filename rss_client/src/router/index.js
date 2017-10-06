import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login/login'
import register from '@/components/register/register'
import home from '@/components/home/home'

Vue.use(Router)

export default new Router({
    routes: [{
        path: '/',
        redirect: '/login'
    }, {
        path: '/login',
        name: 'login',
        component: login
    }, {
        path: '/home',
        name: 'home',
        component: home
    }, {
        path: '/register',
        name: 'register',
        component: register
    }]
})
