import { createRouter, createWebHistory } from 'vue-router';
import store from '../store';
import Login from "@/views/Login.vue"
import SignUp from "@/views/SignUp.vue"
import Home from "@/views/Home.vue"
import Dashboard from "@/views/Dashboard.vue"
import TaskBlocks from "@/views/TaskBlocks"
import Taskblock from "@/views/taskBlock"
import Taskchain from "@/views/TaskChain"
import Mytasks from "@/views/Mytasks"
import CreateTaskBlock from "@/views/CreateTaskBlock"
import MyFriends from "@/views/MyFriends"
import Invitations from "@/views/Invitations"
import AddFriends from "@/views/AddFriends"



const routes = [
  { path: '/login', name: 'Login', component: Login },
  { path: '/signup', name: 'Signup', component: SignUp },
  { path: '/', name: 'Home', component: Home },
  {
    path: '/dashboard',
    name: 'Dashboard',
    meta: { requiresAuth: true },
    component: Dashboard,
    redirect: '/dashboard/taskblocks',
    children: [
      {
        path: 'add-friends',
        name: 'AddFriends',
        component: AddFriends,
      },
      {
        path: 'taskblocks',
        name: 'TaskBlocks',
        component: TaskBlocks,
      },
      {
        path: 'invitations',
        name: 'Invitations',
        component: Invitations,
      },
      {
        path: 'myfriends',
        name: 'MyFriends',
        component: MyFriends,
      },
      {
        path: 'create-taskblock',
        name: 'CreateTaskBlock',
        component: CreateTaskBlock,
      },
      {
        path: 'mytasks',
        name: 'Mytasks',
        component: Mytasks,
      },
      {
        path: 'taskblock/:id',
        name :'Taskblock',
        component:Taskblock,
        props:true
      },
      {
        path: 'taskchain/:id',
        name :'taskchain',
        component:Taskchain,
        props:true
      },
      {
        path: ':pathMatch(.*)*',
        redirect: '/dashboard/taskblocks', // Corrected the spelling
      },
    ],
  },

  // Redirect unknown routes to Home
  {
    path: '/:pathMatch(.*)*',
    redirect: '/',
  },
];

/*
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
},*/


const router = createRouter({
  history: createWebHistory(),
  routes
});


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


export default router;
