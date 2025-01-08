<template>
  <div class="discussion-wrapper">
<div class="discussion-history">
    
        <div class="user-search-container">
        <h2 class="font-semibold text-lg mb-4 text-center">User Directory</h2>
    
        <!-- Search Bar -->
        <input
          type="text"
          v-model="searchQuery"
          placeholder="Search for users..."
          class="search-bar w-full p-2 border border-gray-300 rounded-md mb-4"
        />
    
        <!-- User List -->
        <ul v-if="filteredUsers.length" class="user-list space-y-2">
          <li
            v-for="(user, index) in filteredUsers"
            :key="index"
            class="user-item flex justify-between items-center bg-gray-100 p-3 rounded-md shadow-sm"
          >
            <!-- Username -->
            <span class="username font-medium">{{ user.username }}</span>
    
            <!-- Button or Friend Label -->
            <div>
              <button
                v-if="!isFriend(user.username)"
                @click="sendInvite(user.username)"
                class="send-invite-btn bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded-md"
              >
                Send Invite
              </button>
              <span
                v-else
                class="friend-label text-green-600 font-semibold"
              >
                Friend
              </span>
            </div>
          </li>
        </ul>
    
        <!-- No Users Found -->
        <p v-else class="text-gray-500 text-center">No users match your search.</p>
      </div>
    
    
</div>

    
  </div>
</template>


<script>
export default {
  data() {
    return {
        friends: [
        "JohnDoe",
        "JaneSmith" ,
        "CoolCoder123" ,
        "VueMaster" ,
      ],
      users: [
        { username: "JohnDoe" },
        { username: "JaneSmith" },
        { username: "CoolCoder123" },
        { username: "VueMaster" },
        { username: "NotYourFriend" },
        { username: "FutureFriend" },
      ],
       searchQuery: "",
  
    };
  },
  computed: {
    // Filter users based on search query
    filteredUsers() {
        if (this.searchQuery.toLowerCase().trim() === ""){
            return []
        }
      return this.users.filter((user) =>
        user.username.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
     isFriend(username) {
      // Check if a user is already a friend
      return this.friends.includes(username);
    },
    sendInvite(username) {
      alert(`Invite sent to ${username}!`);
    },
  
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
