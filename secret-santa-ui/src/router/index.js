import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import CreateGroup from "@/views/CreateGroup";
import NotFound from "@/views/NotFound";
import EditGroup from "@/views/EditGroup";
import Imprint from "@/views/Imprint";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/home',
    name: 'blank',
    component: Home
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/createGroup',
    name: 'CreateGroup',
    component: CreateGroup
  },
  {
    path: '/editGroup/:groupId',
    name: 'EditGroup',
    props: true,
    component: EditGroup
  },
  {
    path: '/impressum',
    name: 'Imprint',
    component: Imprint
  },
  {
    path: "*",
    component: NotFound
  }
]

const router = new VueRouter({
  routes
})

export default router
