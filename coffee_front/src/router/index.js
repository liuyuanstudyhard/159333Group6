import VueRouter from 'vue-router';
import HomeView from '../views/HomeView.vue';

import Auth from '../views/Auth.vue';
import UserInfo  from "@/views/UserInfo.vue";
import orderManagement from "@/views/OrderManagement.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/login', // 设置登录页面的路由
    name: 'login',
    component: Auth,
  },
  {
    path: '/userInfo', // 设置登录页面的路由
    name: 'userInfo',
    component: UserInfo,
  },
  {
    path: '/orderManagement',
    name: 'orderManagement',
    component: orderManagement
  }
];

const router = new VueRouter({
  mode: 'history',
  routes
});

export default router;
