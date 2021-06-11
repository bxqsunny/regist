import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  // 户籍管理路由
  {
    path: '/',
    component: Layout,
    redirect: '/regist',
    meta: { title: '户籍管理', icon: '商品管理' },
    children: [
    {
      path: 'regist',
      component: Layout,
      meta: { title: '户籍查询', icon: '模板管理' },
      component: () => import('@/views/regist.vue'),
    }
  ]
  },
  {
    path: '/detail',
    component: Layout,
    redirect: '/detail',
    meta: { title: '迁入迁出管理', icon: '商品管理' },
    children: [
      {
        path: 'detail',
        component: Layout,
        meta: { title: '社区迁入人员', icon: '模板管理' },
        component: () => import('@/views/detail.vue'),
      },
      {
        path: 'emigration',
        component: Layout,
        meta: { title: '社区迁出人员', icon: '模板管理' },
        component: () => import('@/views/emigration.vue'),
      },
      {
        path: 'level',
        component: Layout,
        meta: { title: '统计', icon: '模板管理' },
        component: () => import('@/views/level.vue'),
      }
  ]
  },
  {
    path: '/floor',
    component: Layout,
    redirect: '/floor',
    meta: { title: '楼层管理', icon: '商品管理' },
    children: [
    {
      path: 'floor',
      component: Layout,
      meta: { title: '楼层管理', icon: '模板管理' },
      component: () => import('@/views/floor.vue'),
    },
  ]
  },
  {
    path: '/community',
    component: Layout,
    redirect: '/community',
    meta: { title: '社区管理', icon: '商品管理' },
    children: [
    {
      path: 'community',
      component: Layout,
      meta: { title: '社区管理', icon: '模板管理' },
      component: () => import('@/views/community.vue'),
    },
  ]
  },
  {
    path: '/admin',
    component: Layout,
    redirect: '/admin',
    meta: { title: '系统管理', icon: '商品管理' },
    children: [
    {
      path: 'admin',
      component: Layout,
      meta: { title: '系统信息', icon: '模板管理' },
      component: () => import('@/views/admin.vue'),
    },
    {
      path: 'personage',
      component: Layout,
      meta: { title: '个人信息', icon: '模板管理' },
      component: () => import('@/views/personage.vue'),
    }
  ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
