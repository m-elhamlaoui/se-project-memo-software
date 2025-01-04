import { createRouter, createWebHistory } from 'vue-router';

import store from '../store';


/*
const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/login', name: 'Login', component: Login },
  { path: '/signup', name: 'Signup', component: Signup },
  { 
    path: '/dashboard', 
    name: 'Dashboard', 
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  { 
    path: '/project/:id', 
    name: 'ProjectDetail', 
    component: ProjectDetail,
    meta: { requiresAuth: true }
  },
  // Redirect unknown routes to Home
  { path: '/:pathMatch(.*)*', redirect: '/' }
];

*/
const routes = []
const router = createRouter({
  history: createWebHistory(),
  routes
});

/*
// Navigation Guard for Protected Routes
router.beforeEach((to, from, next) => {
  const isAuthenticated = store.getters['auth/isAuthenticated'];
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login');
  } else {
    if (isAuthenticated && (to.path === '/login' || to.path === '/signup')) {
      return next('/dashboard'); 
    }
    next();
  }
});
*/

export default router;
