<template>
    <div :class="[isCollapsed ? 'w-16' : 'w-64', 'transition-all top-0 left-0 h-screen m-0 flex flex-col bg-gray-900 text-white shadow-lg']">
      <!-- Collapse Button -->
      <div class="p-4">
        <button @click="toggleCollapse" class="flex items-center space-x-2 text-gray-300 hover:text-white">
          <ArrowRightIcon v-if="isCollapsed" class="h-6 w-6" />
          <ArrowLeftIcon v-else class="h-6 w-6" />
          <span v-if="!isCollapsed" class="text-xl">Tools</span>
        </button>
      </div>
  
      <!-- Sidebar Links (Replaced with New Names) -->
      <div class="flex-1">
        <!-- Sidebar Icon with Tooltip -->
        <div :class="[isCollapsed ? 'sidebar-icon' : 'sidebar-icon-not-collapsed', 'group relative flex items-center', 'transition-all duration-300 ease-in-out']">
          <LogoutIcon :class="['h-6 w-6', !isCollapsed ? 'mr-3' : '']" />
          <h1 v-if="!isCollapsed" class="text-white transition-opacity duration-300 ease-in-out opacity-100">
            What is TaskBlock?
          </h1>
          <!-- Tooltip -->
          <span v-if="isCollapsed" class="sidebar-tooltip absolute left-full ml-2 opacity-0 group-hover:opacity-100 transition-opacity duration-200 ease-in-out">
            What is TaskBlock?
          </span>
        </div>
  
        <div :class="[isCollapsed ? 'sidebar-icon' : 'sidebar-icon-not-collapsed', 'group relative flex items-center', 'transition-all duration-300 ease-in-out']">
          <LogoutIcon :class="['h-6 w-6', !isCollapsed ? 'mr-3' : '']" />
          <h1 v-if="!isCollapsed" class="text-white transition-opacity duration-300 ease-in-out opacity-100">
            Wallet
          </h1>
          <!-- Tooltip -->
          <span v-if="isCollapsed" class="sidebar-tooltip absolute left-full ml-2 opacity-0 group-hover:opacity-100 transition-opacity duration-200 ease-in-out">
            Wallet
          </span>
        </div>
  
        <div :class="[isCollapsed ? 'sidebar-icon' : 'sidebar-icon-not-collapsed', 'group relative flex items-center', 'transition-all duration-300 ease-in-out']">
          <LogoutIcon :class="['h-6 w-6', !isCollapsed ? 'mr-3' : '']" />
          <h1 v-if="!isCollapsed" class="text-white transition-opacity duration-300 ease-in-out opacity-100">
            Our voting system
          </h1>
          <!-- Tooltip -->
          <span v-if="isCollapsed" class="sidebar-tooltip absolute left-full ml-2 opacity-0 group-hover:opacity-100 transition-opacity duration-200 ease-in-out">
            Our voting system
          </span>
        </div>
      </div>
  
      <!-- Logout Button at the Bottom -->
      
    </div>
  </template>
  
  <script>
  import { ArrowLeftIcon, ArrowRightIcon, LogoutIcon } from '@heroicons/vue/solid';
  
  export default {
    name: 'Sidebar',
    components: {
      ArrowLeftIcon,
      ArrowRightIcon,
      LogoutIcon
    },
    data() {
      return {
        isCollapsed: false, // Sidebar collapse state
      };
    },
    methods: {
      toggleCollapse() {
        this.isCollapsed = !this.isCollapsed;
      },
      logout() {
        console.log('Logging out...');
      },
    }
  };
  </script>
  
  <style scoped>
  /* Tooltip styles */
  .sidebar-tooltip {
    display: block;
    transform: scale(0);
    transition: opacity 0.2s ease-in-out;
  }
  
  .group:hover .sidebar-tooltip {
    opacity: 1;
    transform: scale(1);
  }
  
  .w-16 {
    width: 4rem; /* Collapsed width */
  }
  
  .text-xl {
    font-size: 1.25rem;
  }
  
  .sidebar-icon {
    cursor: pointer;
    position: relative;
    padding: 10px;
  }
  
  .sidebar-icon .group:hover .sidebar-tooltip {
    display: block;
  }
  </style>
  