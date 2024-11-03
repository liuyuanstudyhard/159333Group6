import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import axios from "axios";
import VueRouter from 'vue-router';
import router from './router';
import store from './store';
import locale from 'element-ui/lib/locale/lang/en'

Vue.prototype.$axios=axios;
axios.defaults.withCredentials = true
Vue.prototype.$httpUrl='http://127.0.0.1:8888'
Vue.config.productionTip = false
//Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(ElementUI,{size:'small',locale});
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
