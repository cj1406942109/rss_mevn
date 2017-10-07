import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/login/login'
import register from '@/components/register/register'
import home from '@/components/home/home'
import reviewList from '@/components/review_list/review_list'
import sysPush from '@/components/sys_push/sys_push'

Vue.use(Router)

var router = new Router({
    routes: [{
        path: '/',
        redirect: '/login'
    }, {
        path: '/login',
        name: 'login',
        component: login
    }, {
        path: '/home',
        meta: {
            requireAuth: true
        },
        component: home,
        children: [{
            path: '',
            redirect: 'review_list'
        }, {
            path: 'review_list',
            meta: {
                requireAuth: true
            },
            component: reviewList
        }, {
            path: 'sys_push',
            meta: {
                requireAuth: true
            },
            component: sysPush
        }]
    }, {
        path: '/register',
        name: 'register',
        component: register
    }],
    beforeEach: (to, from, next) => {
        console.log('hello');

    }
})

router.beforeEach((to, from, next) => {
    if(to.meta.requireAuth) { // 判断该路由是否需要登录权限
        if(sessionStorage.getItem('user')) { // 通过vuex state获取当前的token是否存在
            next();
        } else {
            next({
                path: '/login',
                query: { redirect: to.fullPath } // 将跳转的路由path作为参数，登录成功后跳转到该路由
            })
        }
    } else {
        next();
    }
})
export default router;
