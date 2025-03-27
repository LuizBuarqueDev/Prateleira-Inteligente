import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AuthService from '@/services/AuthService';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/book/:id',
      name: 'BookDetail',
      props: true,
      component: () => import('../views/BookDetail.vue')
    }
    ,
    {
      path: '/authentication',
      name: 'authentication',
      component: () => import('../views/AuthView.vue')
    },

    {
      path: '/bookcase',
      name: 'bookcase',
      meta: { requiresAuth: true },
      component: () => import('../views/BookcaseView.vue')
    },
    {
      path: '/bookregistration',
      name: 'bookregistration',
      meta: { requiresAuth: true },
      component: () => import('../views/BookRegistrationView.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

  if (requiresAuth) {
    if (AuthService.isAuthenticated.value) {
      next();
    } else {
      next('/authentication');
    }
  } else {
    next();
  }
});

export default router
