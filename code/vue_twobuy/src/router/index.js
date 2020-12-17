import Vue from 'vue'
import VueRouter from 'vue-router'
import PhoneLogin from '../components/PhoneLogin.vue'
import EmailLogin from '../components/EmailLogin.vue'
import Home from '../components/Home.vue'
import Register from '../components/Register.vue'
import Admin from '../components/Admin.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/phonelogin'
  },

  {
    path: '/emaillogin',
    component: EmailLogin
  },

  {
    path: '/phonelogin',
    component: PhoneLogin
  },

  {
    path: '/home',
    component: Home
  },

  {
    path: '/register',
    component: Register
  },

  {
    path:'/admin',
    component: Admin
  }
  
]

const router = new VueRouter({
  routes
})

export default router
