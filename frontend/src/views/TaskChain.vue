<template>
  <div class="discussion-wrapper">
    <!-- Return Link -->
    <div class="return-link">
      <a :href="'/dashboard/taskblock/' + this.$route.params.id" class="inline-flex items-center text-green-500 hover:text-green-700 font-medium">
        <span class="mr-2">&larr;</span>
        Back to Dashboard
      </a>
    </div>

    <!-- Discussion History -->
    <div class="discussion-history">
      <div
        v-for="(message, index) in tasks"
        :key="index"
        class="message-card"
      >
        <!-- Message Header -->
        <div class="flex justify-between items-center mb-4">
          <div class="flex items-center space-x-3">
            <div class="h-10 w-10 rounded-full bg-gray-100 flex items-center justify-center">
              <span class="text-gray-700 font-medium">
                {{ message.username.charAt(0).toUpperCase() }}
              </span>
            </div>
            <div>
              <span class="font-bold text-gray-900">{{ message.username }}</span>
              <span class="text-sm text-gray-500 ml-2">{{ message.timestamp }}</span>
            </div>
          </div>
        </div>

        <div
          v-if="message.type === 'task'"
          class="task-card relative"
          @click="toggleTaskDetails(index)"
        >
          <!-- Vote Status Badge -->
          <div
            class="vote-status-tag absolute right-0 top-0 px-3 py-1 rounded-lg text-white text-sm font-medium"
            :class="{
              'bg-yellow-500': message.voteStatus === 'on-going',
              'bg-red-500': message.voteStatus === 'rejected',
              'bg-green-500': message.voteStatus === 'accepted'
            }"
          >
            {{ message.voteStatus }}
          </div>

          <div class="space-y-3 mt-2">
            <!-- Task Title -->
            <h3 class="text-lg font-semibold text-gray-900">{{ message.content.title }}</h3>

            <!-- Task Description -->
            <p class="text-gray-600 text-sm">{{ message.content.description }}</p>

            <!-- Task Metadata -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm">
              <div>
                <p class="text-gray-600">
                  Deadline: 
                  <span class="text-red-500 font-medium">{{ message.content.deadline }}</span>
                </p>
                <p class="text-gray-600">
                  Time Left: 
                  <span class="text-blue-500 font-medium">{{ formatTime(message.timeLeft) }}</span>
                </p>
              </div>
              <div>
                <p class="text-gray-600">
                  Assigned to Users: 
                  <span class="font-medium">{{ message.content.assignedUsers.join(", ") }}</span>
                </p>
                <p class="text-gray-600">
                  Assigned to Subgroups: 
                  <span class="font-medium">{{ message.content.assignedSubgroups.join(", ") }}</span>
                </p>
              </div>
            </div>

            <!-- Voting Progress -->
            <div v-if="message.showStats && showstats" class="mt-4 space-y-4">
              <!-- Accepted Progress -->
              <div class="space-y-1">
                <div class="flex justify-between">
                  <span class="text-sm text-gray-600">Accepted</span>
                  <span class="text-sm font-medium">{{ acceptedPercentage(message) }}%</span>
                </div>
                <div class="bar-container">
                  <div
                    class="bar accepted-bar"
                    :style="{ width: `${acceptedPercentage(message)}%` }"
                  ></div>
                </div>
              </div>

              <!-- Rejected Progress -->
              <div class="space-y-1">
                <div class="flex justify-between">
                  <span class="text-sm text-gray-600">Rejected</span>
                  <span class="text-sm font-medium">{{ rejectedPercentage(message) }}%</span>
                </div>
                <div class="bar-container">
                  <div
                    class="bar rejected-bar"
                    :style="{ width: `${rejectedPercentage(message)}%` }"
                  ></div>
                </div>
              </div>
            </div>

            <!-- Vote Buttons -->
            <div class="flex space-x-4 mt-4">
              <button
                @click.stop="vote(index, 'accept')"
                class="flex-1 py-2 px-4 bg-green-500 hover:bg-green-600 text-white rounded-lg transition-colors"
              >
                Accept
              </button>
              <button
                @click.stop="vote(index, 'reject')"
                class="flex-1 py-2 px-4 bg-red-500 hover:bg-red-600 text-white rounded-lg transition-colors"
              >
                Reject
              </button>
              <div
                v-if="true"
                class="flex-1 py-2 px-4 bg-green-300 text-white rounded-lg text-center"
              >
                Voted
              </div>
            </div>
          </div>
        </div>

        <div v-else class="text-gray-700">{{ message.content }}</div>
      </div>
    </div>

    <!-- Input Bar -->
    <div class="input-bar">
      <button @click="showtaskadd = true" class="send-btn">Send Task</button>
    </div>

    <!-- Add Task Modal -->
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
        <h3 class="text-lg font-semibold">Add Task</h3>
      </template>

      <div class="p-4 space-y-4">
        <form @submit.prevent="addTask">
          <input
            v-model="taskTitle"
            placeholder="Task Title"
            class="input-field"
          />
          
          <textarea
            v-model="taskDescription"
            placeholder="Task Description"
            class="input-field h-24"
          ></textarea>
          
          <input
            v-model="taskDeadline"
            type="datetime-local"
            class="input-field"
          />
          <b-form-group label="Assigned to">
            <div class="input-group mb-3">
              <b-form-tags
                v-model="selectedAssignees"
                :tag-validator="ValidateMember"
                separator=" "
                placeholder="Enter new tags separated by space"
                remove-on-delete
                no-add-on-enter
                class="flex-grow-1"
              ></b-form-tags>
              <b-dropdown
                text="Select a Member"
                class="ml-2"
                variant="warning"
                v-if="availableMembers.length"
              >
                <b-dropdown-item
                  v-for="(option, index) in availableMembers"
                  :key="index"
                  @click="addAssignedto(option)"
                >
                  {{ option }}
                </b-dropdown-item>
              </b-dropdown>
            </div>
          </b-form-group>
          <b-form-group label="Assigned to Subgroups">
            <div class="input-group mb-3">
              <b-form-tags
                v-model="selectedSubgroup"
                :tag-validator="ValidateSubgroup"
                separator=" "
                placeholder="Enter new tags separated by space"
                remove-on-delete
                no-add-on-enter
                class="flex-grow-1"
              ></b-form-tags>
              <b-dropdown
                text="Select Subgroup"
                class="ml-2"
                variant="warning"
                v-if="availableSubgroups.length"
              >
                <b-dropdown-item
                  v-for="(option, index) in availableSubgroups"
                  :key="index"
                  @click="addSubgroup(option)"
                >
                  @ {{ option }}
                </b-dropdown-item>
              </b-dropdown>
            </div>
          </b-form-group>
          <button @click="sendTask" class="send-btn w-full">Create Task</button>
          
        </form>
      </div>
    </b-modal>
  </div>
</template>


<script>
export default {
  data() {
    return {
    showtaskadd:false,
      newTask: '',
      showstats:true,
      tasks: [
        {
          username: "John Doe",
          timestamp: "2025-01-08 10:30",
          type: "task",
          voteStatus: "on-going", // "accepted", "rejected", or "on-going"
          content: {
            title: "Improve Dashboard UI",
            description: "Redesign the iwef weofimwoiemf eoifmweoifwef efoiwmefoimwefwef eiomewfoiwef ewfoimwefoimwef wefoimwefoimwef weoimwoeimfwef oimowemfw efoimef wefoimwef weoifm ewfowief oimdashboard to enhance user experience and improve navigation.",
              deadline:"2021-10-16",
              assignedUsers:["hi","ef","efef","efoine"],
              assignedSubgroups:["@frontend tee","wefwef"]

          },
          showStats: false,
          votes: {
            accepted: 15,
            rejected: 5,
          },
          timeLeft:5,

        },
        {
          username: "John Doe",
          timestamp: "2025-01-08 10:30",
          type: "task",
          voteStatus: "on-going", // "accepted", "rejected", or "on-going"
          content: {
            title: "Improve Dashboard UI",
            description: "Redesign the iwef weofimwoiemf eoifmweoifwef efoiwmefoimwefwef eiomewfoiwef ewfoimwefoimwef wefoimwefoimwef weoimwoeimfwef oimowemfw efoimef wefoimwef weoifm ewfowief oimdashboard to enhance user experience and improve navigation.",
            deadline:"2021-10-16",
            assignedUsers:["hi","ef","efef","efoine"],
            assignedSubgroups:["@frontend tee","wefwef"]

          },
          showStats: false,
          timeLeft:5,
          votes: {
            accepted: 15,
            rejected: 5,
          },
        },
      ],
      filteredMembers:[],
      taskTitle: '',
      taskDescription: '',
      taskDeadline: '',
      selectedAssignees: [],
      selectedSubgroup: [],
      availableMembers: ['Alice', 'Bob', 'Charlie'], // Example member list
      availableSubgroups: ['Frontend Team', 'Backend Team', 'Design Team'] ,// Example subgroup list
       countdownInterval: null,
    };
  },
  mounted() {
    // Start updating countdown every second
    this.updateCountdowns();
  },

  beforeDestroy() {
    // Clear the interval on component destruction to prevent memory leaks
    if (this.countdownInterval) {
      clearInterval(this.countdownInterval);
    }
  },
  methods: {
    async addTask() {


    },
    formatTime(seconds) {
      if (seconds <= 0) return "Expired";

      const days = Math.floor(seconds / (60 * 60 * 24));
      const hours = Math.floor((seconds % (60 * 60 * 24)) / (60 * 60));
      const minutes = Math.floor((seconds % (60 * 60)) / 60);
      const secs = seconds % 60;

      return `${days}d:${hours}h:${minutes}m:${secs}s`;
    },

    updateCountdowns() {
      this.countdownInterval = setInterval(() => {
        this.tasks.forEach((task) => {
          if (task.timeLeft > 0) {
            task.timeLeft--; // Decrease the remaining time
          }
        });
      }, 1000);
    },

   formatTime(seconds) {
      if (seconds <= 0) return "Expired";

      const days = Math.floor(seconds / (60 * 60 * 24));
      const hours = Math.floor((seconds % (60 * 60 * 24)) / (60 * 60));
      const minutes = Math.floor((seconds % (60 * 60)) / 60);
      const secs = seconds % 60;

      return `${days}d:${hours}h:${minutes}m:${secs}s`;
    },

    vote(index, type) {
      if (type === "accept") {
        this.tasks[index].votes.accepted++;
      } else if (type === "reject") {
        this.tasks[index].votes.rejected++;
      }

      

    
    },
    toggleTaskDetails(index) {
      this.tasks[index].showStats = !this.tasks[index].showStats;
    },
    acceptedPercentage(message) {
      const totalVotes = message.votes.accepted + message.votes.rejected;
      return totalVotes === 0 ? 0 : Math.round((message.votes.accepted / totalVotes) * 100);
    },
    rejectedPercentage(message) {
      const totalVotes = message.votes.accepted + message.votes.rejected;
      return totalVotes === 0 ? 0 : Math.round((message.votes.rejected / totalVotes) * 100);
    },
     handleTagState(event) {

      const searchtag = event.target.value;
     
      if (!searchtag) {
        this.filteredMembers = []
      }
      else{
         this.filteredMembers = this.availableMembers.filter((user) => {
          const username = user.toLowerCase();
          return username.includes(searchtag.toLowerCase()) && !this.selectedAssignees.includes(user)  ;  
      });

      }

    
      
    }
    ,
    ValidateMember(tag){
      
      return (this.availableMembers.includes(tag) && !this.selectedAssignees.includes(tag))

    }, 
    ValidateSubgroup(tag){
      
      return (this.availableSubgroups.includes(tag) && !this.availableSubgroups.includes(tag))

    }, 
     addAssignedto(tag) {
      if (!this.selectedAssignees.includes(tag)) {
        this.selectedAssignees.push(tag);
      }
    },
    addSubgroup(tag){
         if (!this.selectedSubgroup.includes(tag)) {
        this.selectedSubgroup.push(tag);
      }

    }
  },
};
</script>

<style scoped>
.discussion-wrapper {
  display: flex;
  width:100%;
  flex-direction: column;
  height: 100vh;
  background-color: rgba(229, 231, 235, 0.8);
}

.return-link {
  padding: 16px;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  margin: 16px;
}

.discussion-history {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  margin: 16px;
}

.message-card {
  margin-bottom: 16px;
  padding: 12px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background-color: white;
  transition: transform 0.3s ease-in-out, background-color 0.3s;
}

.vote-status-tag {
  transform: translateY(-50%);
}

.message-card:hover {
  transform: scale(1.02);
  background-color: rgba(229, 231, 235, 0.8);
}

.task-card {
  cursor: pointer;
}

.task-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}

.progress-bar {
  margin-top: 8px;
}

.bar-container {
  background-color: #e5e7eb;
  height: 8px;
  border-radius: 4px;
  overflow: hidden;
}

.bar {
  height: 8px;
  transition: width 0.3s;
}

.accepted-bar {
  background-color: #4caf50;
}

.rejected-bar {
  background-color: #f44336;
}

.chain-animation .chain-icon {
  width: 32px;
  height: 32px;
  stroke: #4caf50;
}

.input-bar {
  display: flex;
  gap: 12px;
  padding: 16px;
  background-color: white;
  border-top: 1px solid #e5e7eb;
}

.input-field {
  flex: 1;
  padding: 8px 12px;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
  font-size: 16px;
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

.input-bar {
  background: rgba(255, 255, 255, 0.9);
  padding: 16px;
  border-radius: 8px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* Task Input */
.input-field {
  padding: 8px 12px;
  border-radius: 4px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  font-size: 14px;
  margin-bottom: 8px;
}

/* Dropdown for Assigning Members */
.dropdown {
  display: flex;
  align-items: center;
  gap: 8px;
}
.dropdown-select {
  padding: 8px 12px;
  border-radius: 4px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  font-size: 14px;
  min-width: 150px;
}
.dropdown-add-btn {
  background: rgba(0, 100, 200, 0.8);
  color: white;
  padding: 8px 12px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  transition: background 0.3s;
}
.dropdown-add-btn:hover {
  background: rgba(0, 100, 200, 1);
}

/* Subgroups Tags Input */
.subgroups-input {
  display: flex;
  flex-direction: column;
}
.subgroup-input-field {
  border: 1px solid rgba(0, 0, 0, 0.2);
  padding: 8px;
  border-radius: 4px;
  font-size: 14px;
  margin-bottom: 8px;
  width: 100%;
}
.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 8px;
}
.tag {
  background: rgba(0, 0, 0, 0.1);
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 14px;
  display: flex;
  align-items: center;
}
.tag-remove-btn {
  margin-left: 8px;
  background: none;
  border: none;
  color: rgba(200, 0, 0, 0.8);
  cursor: pointer;
  font-size: 16px;
}


</style>
