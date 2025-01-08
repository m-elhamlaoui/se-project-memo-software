<template>
  <div class="dashboard-container bg-gray-50 h-screen flex flex-col overflow-hidden">
    <!-- Header Card - Fixed at top -->
    <div class="header-card bg-white shadow-sm p-4 flex-shrink-0">
      <div class="space-y-4">
        <!-- Group Name -->
        <h1 class="text-xl font-bold text-gray-800">{{ groupName }}</h1>
        
        <!-- Stats Row -->
        <div class="flex flex-wrap gap-4">
          <div class="flex items-center text-gray-600 text-sm">
            <span class="font-medium">Admin:</span>
            <span class="ml-2">{{ admin }}</span>
          </div>
          <div class="flex items-center gap-2 text-gray-600 text-sm">
            <ClockIcon class="h-4 w-4 text-gray-500" />
            <span>{{ formattedTime }}</span>
          </div>
          <div class="flex items-center gap-2 text-gray-600 text-sm">
            <ChartBarIcon class="h-4 w-4 text-gray-500" />
            <span>{{ percentage }}% Complete</span>
          </div>
        </div>

        <!-- Aura Indicator -->
        <div class="flex items-center justify-center gap-2 bg-gradient-to-r from-green-500 to-green-600 text-white p-2 rounded-lg w-40">
          <CubeIcon class="h-4 w-4" />
          <span class="font-medium text-sm">{{ aura }} Aura</span>
        </div>
      </div>
    </div>

    <!-- Scrollable Dashboard Content -->
    <div class="flex-1 overflow-y-auto p-4 space-y-4">
      <!-- Enter Task Chain Card -->
      <div 
        class="h-32 bg-gradient-to-br from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 transition-all hover:shadow-lg cursor-pointer rounded-lg"
        @click="handleEnterTaskChain"
      >
        <div class="h-full flex items-center justify-center">
          <span class="text-lg font-medium text-white">Enter Task Chain</span>
        </div>
      </div>

      <!-- Members Card -->
      <div class="dashboard-card">
        <div class="flex items-center justify-between mb-2">
          <h2 class="text-lg font-medium">Team Members</h2>
          <button 
            @click="showAddMemberModal = true"
            class="p-1 rounded-full hover:bg-gray-100"
          >
            <PlusIcon class="h-5 w-5 text-gray-600" />
          </button>
        </div>
        <div class="overflow-y-auto max-h-40 pr-2 space-y-1">
          <div 
            v-for="(member, index) in members" 
            :key="index"
            class="flex items-center justify-between p-2 rounded-lg hover:bg-gray-50"
          >
            <span class="text-sm">{{ member }}</span>
            <ChevronRightIcon class="h-4 w-4 text-gray-400" />
          </div>
        </div>
      </div>

      <!-- Pending Tasks Card -->
      <div class="dashboard-card">
        <h2 class="text-lg font-medium mb-2">Pending Tasks</h2>
        <div class="overflow-y-auto max-h-40 pr-2 space-y-1">
          <div 
            v-for="(task, index) in pendingTasks" 
            :key="index"
            class="flex items-center justify-between p-2 rounded-lg hover:bg-gray-50"
          >
            <div class="flex items-center gap-2">
              <ClockIcon class="h-4 w-4 text-gray-500" />
              <span class="text-sm">{{ task }}</span>
            </div>
            <ChevronRightIcon class="h-4 w-4 text-gray-400" />
          </div>
        </div>
      </div>

      <!-- Voting Tasks Card -->
      <div class="dashboard-card">
        <h2 class="text-lg font-medium mb-2">Tasks in Vote Phase</h2>
        <div class="overflow-y-auto max-h-40 pr-2 space-y-1">
          <div 
            v-for="(task, index) in votingTasks" 
            :key="index"
            class="flex items-center justify-between p-2 rounded-lg hover:bg-gray-50"
          >
            <div class="flex items-center gap-2">
              <ChartBarIcon class="h-4 w-4 text-gray-500" />
              <span class="text-sm">{{ task }}</span>
            </div>
            <ChevronRightIcon class="h-4 w-4 text-gray-400" />
          </div>
        </div>
      </div>
    </div>

    <!-- Add Member Modal -->
    <b-modal 
      v-model="showAddMemberModal"
      title="Add Member"
      centered
      hide-footer
    >
      <div class="p-4">
        <b-form-group label="Add Member">
          <div class="input-group mb-3">
            <b-form-tags
              v-model="selectedMembers"
              :tag-validator="validateMember"
              separator=" "
              placeholder="Enter new members separated by space"
              remove-on-delete
              no-add-on-enter
              class="flex-grow-1"
            ></b-form-tags>
            <b-dropdown
              v-if="availableMembers.length"
              text="Select a Member"
              class="ml-2"
              variant="success"
            >
              <b-dropdown-item
                v-for="(member, index) in availableMembers"
                :key="index"
                @click="addMember(member)"
              >
                {{ member }}
              </b-dropdown-item>
            </b-dropdown>
          </div>
        </b-form-group>
        <button 
          @click="handleAddMembers"
          class="w-full bg-green-500 hover:bg-green-600 text-white font-medium py-2 px-4 rounded-lg transition-colors"
        >
          Add Members
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script>
import { ClockIcon, ChartBarIcon, BoxIcon, PlusIcon, ChevronRightIcon,CubeIcon } from '@heroicons/vue/outline'

export default {
  name: 'DashboardView',
  
  components: {
    ClockIcon,
    ChartBarIcon,
    BoxIcon,
    PlusIcon,
    ChevronRightIcon,
    CubeIcon
  },

  data() {
    return {
      groupName: 'Development Sprint Q1',
      admin: 'JohnDoe',
      timePeriod: 172800,
      percentage: 87,
      aura: 100,
      members: ['Sarah Chen', 'Mike Ross', 'Emily Wang', 'David Kim', 'Alex Johnson', 'Lisa Park', 'Tom Wilson'],
      pendingTasks: [
        'API Integration', 
        'User Authentication', 
        'Database Setup', 
        'UI Components',
        'Testing Framework',
        'Documentation',
        'Performance Optimization'
      ],
      votingTasks: [
        'Feature Prioritization', 
        'Tech Stack Decision',
        'Design System Implementation',
        'Deployment Strategy'
      ],
      showAddMemberModal: false,
      selectedMembers: [],
      availableMembers: ['John Smith', 'Jane Doe', 'Bob Wilson']
    }
  },

  computed: {
    formattedTime() {
      const days = Math.floor(this.timePeriod / 86400)
      const hours = Math.floor((this.timePeriod % 86400) / 3600)
      const minutes = Math.floor((this.timePeriod % 3600) / 60)
      const seconds = this.timePeriod % 60
      return `${days}d:${hours}h:${minutes}m:${seconds}s`
    }
  },

  methods: {
    handleEnterTaskChain() {
      console.log('Entering task chain...')
    },

    validateMember(member) {
      return this.availableMembers.includes(member) && 
             !this.selectedMembers.includes(member) && 
             !this.members.includes(member)
    },

    addMember(member) {
      if (this.validateMember(member)) {
        this.selectedMembers.push(member)
      }
    },

    handleAddMembers() {
      this.members = [...this.members, ...this.selectedMembers]
      this.selectedMembers = []
      this.showAddMemberModal = false
    }
  }
}
</script>

<style scoped>
.dashboard-card {
  @apply bg-white rounded-lg shadow-sm p-4;
}

.dashboard-container {
  width: 100%;
  margin: 0 auto;
}

/* Custom scrollbar styles */
.overflow-y-auto {
  scrollbar-width: thin;
  scrollbar-color: #CBD5E0 transparent;
}

.overflow-y-auto::-webkit-scrollbar {
  width: 4px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: transparent;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background-color: #CBD5E0;
  border-radius: 20px;
}

/* Hover effect for scrollbar */
.overflow-y-auto:hover::-webkit-scrollbar-thumb {
  background-color: #A0AEC0;
}
</style>