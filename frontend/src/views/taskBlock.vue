<template>
<div class="wrapp">

  <div class="welcome-message space-y-4 p-4 bg-gray-100 rounded-md shadow-lg">
    <!-- Group Name -->
    <div class="overflow-hidden text-2xl font-bold text-gray-800" ref="welcomeText">
      TaskBlock Name: {{ groupName }}
    </div>

    

    <!-- Time Period & Percentage -->
    <div class="flex flex-row items-center space-x-6">
      <!-- Admin Info -->
    <div class="admin-info text-gray-600 text-sm">
      Admin: <span class="font-medium"> {{ admin }}</span>
    </div>

      <!-- Time Period -->
      <div class="time-period flex items-center space-x-2 text-gray-700">
        <ClockIcon class="w-5 h-5 text-gray-500" />
        <span>Time Policy: {{ formattedTime }}</span>
      </div>

      <!-- Percentage -->
      <div class="percentage flex items-center space-x-2 text-gray-700">
        <ChartBarIcon class="w-5 h-5 text-gray-500" />
        <span> Pourcentage Policy: {{ percentage }}%</span>
      </div>

    </div>

    <!-- Aura Section -->
    <div class="aura flex flex-row items-center justify-center bg-green-500 text-white rounded-lg p-2">
      <div class="font-bold text-lg mr-2">
        {{ aura }}
      </div>
      <CubeIcon class="w-5 h-5 mr-2" />
      <div>Aura</div>
    </div>
  </div>
  
  
    <div class="dashboard-wrapper">
      <!-- Top Left Card - Enter -->
      <div class="dashboard-card enter-card" @click="goToEnterPage">
        <p>Enter Task Chain</p>
      </div>
  
      <!-- Top Right Card - List of members -->
      <div class="dashboard-card scrollable-card" @click="goToUserList">
        
<div class="flex w-full flex-row items-center justify-between">
  <h3 class="flex items-center">Members</h3>
  <PlusSmIcon @click="showtaskadd = true" class="transition-all rounded-lg bg-green-500 hover:bg-green-300 h-10 w-10" />
</div>        
<div class="scrollable-content">
          <div v-for="(user, index) in members" :key="index" class="username-item">
            {{ user }}
          </div>
        </div>
      </div>
  
      <!-- Bottom Left Card - Pending Tasks -->
      <div class="dashboard-card scrollable-card" @click="goToPendingTasks">
        <h3>Pending Tasks</h3>
        <div class="scrollable-content">
          <div v-for="(task, index) in pendingTasks" :key="index" class="task-item">
            {{ task }}
          </div>
        </div>
      </div>
  
      <!-- Bottom Right Card - Tasks in Vote Phase -->
      <div class="dashboard-card scrollable-card" @click="goToVotingTasks">
        <h3>Tasks in Vote Phase</h3>
        <div class="scrollable-content">
          <div v-for="(task, index) in votingTasks" :key="index" class="task-item">
            {{ task }}
          </div>
        </div>
      </div>
    </div>
</div>


   <b-modal
      id="detail-modal"
      size="lg"
      hide-footer   
      v-model="showtaskadd"
      @hidden="showtaskadd = false"
      centered
      class="slide-in"


    >
      <template #modal-title>
        Add Member
      </template>
      <!-- start righting here -->
       <div class="input-bar">
   

     <b-form-group label="Add Member">
      <div class="input-group mb-3">
        <b-form-tags
          label="Add Member"
          input-id="tags-remove-on-delete"
          :input-attrs="{ 'aria-describedby': 'tags-remove-on-delete-help' }"
          v-model="selectedAssignees"
          :tag-validator="ValidateMember"
          separator=" "
          placeholder="Enter new tags separated by space"
          remove-on-delete
          no-add-on-enter
          class="flex-grow-1"
        ></b-form-tags>
        <b-dropdown
          id="dropdown-1"
          text="Select a Member"
          class="ml-2"
          style="background-color:orange;border-color:orange"

          v-if="members.length"
        >
          <b-dropdown-item
            v-for="(option, index) in availablemembers"
            :key="index"
            @click="addAssignedto(option)"
          >
            {{ option }}
          </b-dropdown-item>
        </b-dropdown>
      </div>
    </b-form-group>


    <!-- Send Task Button -->
    <button @click="sendTask" class="send-btn bg-green-400">Create  Task</button>
  </div>

    </b-modal>
</template>

<script>
import {CubeIcon,PlusSmIcon,ChartBarIcon,ClockIcon} from "@heroicons/vue/solid"
export default {
  components:{CubeIcon,PlusSmIcon,ChartBarIcon,ClockIcon},
  computed: {
    // Format the time period into d:h:m:s
    formattedTime() {
      const days = Math.floor(this.timePeriod / (24 * 3600));
      const hours = Math.floor((this.timePeriod % (24 * 3600)) / 3600);
      const minutes = Math.floor((this.timePeriod % 3600) / 60);
      const seconds = this.timePeriod % 60;
      return `${days}d:${hours}h:${minutes}m:${seconds}s`;
    },
  },
  data() {
    return {
      groupName: "hehehehehe", // Example group name
      admin: "JohnDoe", // Example admin username
      timePeriod: 973658, // Time period in seconds (example)
      percentage: 87, // Example percentage
      aura: 100, // Aura value
      // Dummy data for the lists
      members: ['user1', 'user2', 'user3', 'user4'],
      availablemembers:['user1'],
      pendingTasks: ['Task 1', 'Task 2', 'Task 3', 'Task 4'],
      votingTasks: ['Vote Task 1', 'Vote Task 2'],
      showtaskadd:false,
      selectedAssignees: [],


    };
  },
  methods: {
    goToEnterPage() {
      console.log('Navigating to Enter page');
      // Add navigation logic
    },
    goToUserList() {
      console.log('Navigating to User List');
      // Add navigation logic
    },
    goToPendingTasks() {
      console.log('Navigating to Pending Tasks');
      // Add navigation logic
    },
    goToVotingTasks() {
      console.log('Navigating to Voting Tasks');
      // Add navigation logic
    },
     ValidateMember(tag){
      
      return (this.availablemembers.includes(tag) && !this.selectedAssignees.includes(tag) && !this.members.includes(tag))

    }, 
     addAssignedto(tag) {
      if (!this.selectedAssignees.includes(tag) && !this.members.includes(tag)) {
        this.selectedAssignees.push(tag);
      }
    },
  }
};
</script>

<style scoped>
/* Wrapper for the welcome message */

.welcome-message {
  margin: 0 auto;
  height: 24vh;
}

.text-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.aura {
  display: flex;
  align-items: center;
  justify-content: center;
}

.admin-info {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.time-period,
.percentage {
  display: flex;
  align-items: center;
}



@keyframes blink {
  50% {
    opacity: 0;
  }
}
.text-title{
  display: flex;
  margin-left: 100px;
  width: 100%;
  align-items: center;
  border-bottom: #555;
}
.wrapp{
  width: 100%;
  height: 100%;
  flex-direction: column;

}
.dashboard-wrapper {
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 20px;
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.2); /* Optional: add slight background for the blur effect */
  backdrop-filter: blur(10px); /* Apply blur effect on the background */
}

.dashboard-card {
  background-color: rgba(255, 255, 255, 0.8); /* Semi-transparent white */
  border-radius: 12px;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.dashboard-card:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.dashboard-card h3 {
  font-size: 18px;
  margin-bottom: 10px;
}

.scrollable-card .scrollable-content {
  max-height: 150px;
  overflow-y: auto;
}


.scrollable-content{
    background-color: rgba(0, 200, 0, 0.3); /* Optional: add slight background for the blur effect */
    backdrop-filter: blur(10px); /* Apply blur effect on the background */
    border-radius: 1rem

}
.scrollable-card:hover::-webkit-scrollbar {
  opacity: 1;
}

/* Style the scrollbar track */
.scrollable-card::-webkit-scrollbar-track {
  background-color: transparent;
}

/* Style the scrollbar thumb (the draggable part) */
.scrollable-card::-webkit-scrollbar-thumb {
  border-radius: 10px;
  transition: background-color 0.3s ease;
}

/* Change the scrollbar thumb color when hovered */
.scrollable-card:hover::-webkit-scrollbar-thumb {
  background-color: #555; /* Darker gray when hovered */
}

.username-item, .task-item {
  padding: 8px 0;
  font-size: 16px;
  border-bottom: 1px solid #eee;
  text-overflow: ellipsis;
  margin-left: 5px ;
}

.enter-card {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #4CAF50; /* Green for Enter card */
  color: white;
  font-size: 20px;
  font-weight: bold;
}

.scrollable-card {
  display: flex;
  flex-direction: column;
}

.scrollable-content {
  margin-top: 10px;
}
.send-btn {
  background-color: rgb(36, 162, 36);
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  font-size: 16px;
  transition: background-color 0.3s;
}

.send-btn:hover {
  background-color: rgb(2, 102, 2);
}
</style>
