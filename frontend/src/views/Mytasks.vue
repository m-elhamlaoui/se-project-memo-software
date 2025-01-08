<template>
  <div class="discussion-wrapper">
    <!-- Return Link -->
    <div class="return-link">
      <a href="/" class="inline-flex items-center text-green-500 hover:text-green-700 font-medium group">
        <span class="mr-2 transform group-hover:-translate-x-1 transition-transform">&larr;</span>
        Back to Dashboard
      </a>
    </div>

    <!-- Discussion History -->
    <div class="discussion-history">
      <TransitionGroup 
        name="list" 
        tag="div" 
        class="space-y-4"
      >
        <div
          v-for="(message, index) in tasks"
          :key="index"
          class="message-card transform hover:scale-[1.01] transition-all duration-200"
        >
          <!-- Message Header -->
          <div class="flex items-center justify-between mb-4 pb-3 border-b border-gray-100">
            <div class="flex items-center space-x-3">
              <!-- User Avatar -->
              <div class="h-10 w-10 rounded-full bg-gradient-to-br from-green-100 to-green-200 flex items-center justify-center flex-shrink-0">
                <span class="text-green-700 font-medium">
                  {{ message.username.charAt(0).toUpperCase() }}
                </span>
              </div>
              <div class="min-w-0">
                <h3 class="font-bold text-gray-900 truncate">{{ message.username }}</h3>
                <p class="text-sm text-gray-500">{{ message.timestamp }}</p>
              </div>
            </div>
          </div>

          <div
            v-if="message.type === 'task'"
            class="task-card"
            @click="toggleTaskDetails(index)"
          >
            <div class="space-y-4">
              <!-- Task Content -->
              <div class="space-y-2">
                <h4 class="task-title text-xl font-semibold text-gray-900">
                  {{ message.content.title }}
                </h4>
                <p class="text-gray-600 text-sm leading-relaxed">
                  {{ message.content.description }}
                </p>
              </div>

              <!-- Task Metadata -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <!-- Deadline -->
                <div class="space-y-2">
                  <p class="text-sm text-gray-600">
                    <span class="inline-block w-20 font-medium">Deadline:</span>
                    <span class="text-red-500">{{ message.content.deadline }}</span>
                  </p>
                </div>

                <!-- Assignments -->
                <div class="space-y-2">
                  <!-- Assigned Subgroups -->
                  <div class="flex flex-wrap gap-2">
                    <span class="text-sm text-gray-600 font-medium">Subgroups:</span>
                    <div class="flex flex-wrap gap-1">
                      <span 
                        v-for="(subgroup, idx) in message.content.assignedSubgroups" 
                        :key="idx"
                        class="inline-flex items-center px-2 py-1 bg-gray-100 rounded-full text-xs text-gray-700"
                      >
                        @{{ subgroup }}
                      </span>
                    </div>
                  </div>

                  <!-- Assigned Users -->
                  <div class="flex flex-wrap gap-2">
                    <span class="text-sm text-gray-600 font-medium">Users:</span>
                    <div class="flex flex-wrap gap-1">
                      <span 
                        v-for="(user, idx) in message.content.assignedUsers" 
                        :key="idx"
                        class="inline-flex items-center px-2 py-1 bg-blue-100 rounded-full text-xs text-blue-700"
                      >
                        {{ user }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-else class="text-gray-700">{{ message.content }}</div>
        </div>
      </TransitionGroup>
    </div>
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
      availableSubgroups: ['Frontend Team', 'Backend Team', 'Design Team'] // Example subgroup list
    };
  },
  methods: {
    addTask() {
     
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
