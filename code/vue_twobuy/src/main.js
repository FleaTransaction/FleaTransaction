import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
import axious from 'axios'

//配置请求的根路径
axious.defaults.baseURL = 'http://localhost:8888'

Vue.config.productionTip = false
Vue.prototype.$http = axious
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
