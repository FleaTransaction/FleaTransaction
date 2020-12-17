import Vue from 'vue'
import { Button } from 'element-ui'
import {Form,FormItem} from 'element-ui'
import { Input } from 'element-ui'
import { Message } from 'element-ui'
import {Menu,MenuItem,MenuItemGroup,Submenu} from 'element-ui'
import {Icon} from 'element-ui'
import {Tabs,TabPane} from 'element-ui'

Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(Icon)
Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.prototype.$message=Message
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Submenu)