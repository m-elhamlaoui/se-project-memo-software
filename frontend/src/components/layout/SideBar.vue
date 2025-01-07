<template>
  <div :class="[
    isCollapsed ? 'w-16' : 'w-64', 
    'transition-all top-0 left-0 h-screen m-0 flex flex-col bg-gray-900 text-white shadow-lg'
  ]">
    <!-- Collapse Button -->
    <div class="p-4">
      <button
        @click="toggleCollapse"
        class="flex items-center space-x-2 text-gray-300 hover:text-white"
      >
        <ArrowRightIcon v-if="isCollapsed" class="h-6 w-6" />
        <ArrowLeftIcon v-else class="h-6 w-6" />
        <span v-if="!isCollapsed" class="text-xl">Tools</span>
      </button>
    </div>

    <!-- Sidebar Icons -->
   <!-- Sidebar Icons -->
<div class="flex-1">
    <!-- Sidebar Icon with Tooltip -->
    <div :class="[isCollapsed ? 'sidebar-icon' : 'sidebar-icon-not-collapsed', 'group relative flex items-center', 'transition-all duration-300 ease-in-out']">
      <LogoutIcon :class="['h-6 w-6', !isCollapsed ? 'mr-3':'']" />
      <h1 v-if="!isCollapsed" class="text-white transition-opacity duration-300 ease-in-out opacity-100">
      TaskBlocks
    </h1>
      <!-- Tooltip -->
      <span v-if="isCollapsed" class="sidebar-tooltip absolute left-full ml-2 opacity-0 group-hover:opacity-100 transition-opacity duration-200 ease-in-out">
        TaskBlocks
      </span>
    </div>



    <div :class="[isCollapsed ? 'sidebar-icon' : 'sidebar-icon-not-collapsed', 'group relative flex items-center', 'transition-all duration-300 ease-in-out']">
      <LogoutIcon :class="['h-6 w-6', !isCollapsed ? 'mr-3':'']" />
      <h1 v-if="!isCollapsed" class="text-white transition-opacity duration-300 ease-in-out opacity-100">
      My tasks
    </h1>
      <!-- Tooltip -->
      <span v-if="isCollapsed" class="sidebar-tooltip absolute left-full ml-2 opacity-0 group-hover:opacity-100 transition-opacity duration-200 ease-in-out">
        My tasks
      </span>
    </div>

    
    
</div>

    <!-- Logout Button at the Bottom -->
    <div class="p-4 mt-auto">
  <div class="flex items-center justify-center  group relative">
    <button
      @click="logout"
      class="flex items-center space-x-2 text-red-200 hover:text-red-500"
    >
      <LogoutIcon class="h-6 w-6" />
      <h1 v-if="!isCollapsed">Logout</h1>
    </button>
    <!-- Tooltip for Logout -->
    <span class="sidebar-tooltip absolute left-full ml-2 opacity-0 group-hover:opacity-100 transition-opacity duration-200">
      Logout
    </span>
  </div>
</div>
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
      isCollapsed: false, // Toggle for sidebar collapse state
    };
  },
  methods: {
    toggleCollapse() {
      this.isCollapsed = !this.isCollapsed;
    },
    logout() {
      console.log('Logging out...');
    }
  }
};
</script>

<style scoped>
/* Add some basic styling for the tooltip */
.sidebar-tooltip {
  display: block;
  transform: scale(0);
  transition: opacity 0.2s ease-in-out;
}

/* Always show tooltip on hover, even in collapsed state */
.group:hover .sidebar-tooltip {
  opacity: 1;
  transform: scale(1);
}

/* Style adjustments for collapse behavior */
.w-16 {
  width: 4rem; /* Adjust width for collapsed sidebar */
}

.text-xl {
  font-size: 1.25rem;
}

.sidebar-icon {
  cursor: pointer;
  position: relative;
}

.sidebar-icon .group:hover .sidebar-tooltip {
  display: block;
}
</style>
