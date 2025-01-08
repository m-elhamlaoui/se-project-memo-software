<template>
  <div class="h-screen w-[100%]  flex flex-col bg-gray-50">
    <!-- Main Container - fills entire height and width -->
    <div class="w-full h-full flex flex-col overflow-hidden">
      <!-- Header Section - fixed height -->
      <div class="bg-gradient-to-r from-green-500 to-green-600 p-4 flex-shrink-0">
        <h2 class="text-xl font-bold text-white mb-2">Find Friends</h2>
    <div class="relative">
  <input
    type="text"
    v-model="searchQuery"
    placeholder="Type to search users..."
    class="w-full py-3 rounded-lg pl-12 pr-4 bg-white/10 backdrop-blur-sm text-white placeholder-white/70 border border-white/20 focus:outline-none focus:ring-2 focus:ring-white/50 transition-all"
    @focus="handleFocus"
  />
  <div class="absolute left-4 top-1/2 -translate-y-1/2 pointer-events-none">
    <SearchIcon class="h-5 w-5 text-white/70" />
  </div>
</div> 



      </div>

      <!-- Results Section - scrollable -->
      <div class="flex-1 overflow-y-auto p-4">
        <div v-if="searchQuery" class="space-y-2">
          <!-- Loading State -->
          <div v-if="isLoading" class="flex items-center justify-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
          </div>

          <!-- Results List -->
          <div v-else>
            <transition-group 
              name="list" 
              tag="ul" 
              class="space-y-2"
            >
              <li
                v-for="(user, index) in filteredUsers"
                :key="user.username"
                class="bg-white rounded-lg p-4 flex items-center justify-between transform hover:scale-[1.01] transition-all cursor-pointer shadow-sm"
                :style="{ animationDelay: `${index * 50}ms` }"
              >
                <div class="flex items-center space-x-4">
                  <div class="w-10 h-10 rounded-full bg-gradient-to-br from-green-100 to-green-200 flex items-center justify-center flex-shrink-0">
                    <span class="text-black-600 font-medium">
                      {{ user.username.charAt(0).toUpperCase() }}
                    </span>
                  </div>
                  <div class="min-w-0">
                    <h3 class="font-medium text-gray-900 truncate">{{ user.username }}</h3>
                    <p class="text-sm text-gray-500">
                      {{ isFriend(user.username) ? 'Friend' : 'User' }}
                    </p>
                  </div>
                </div>

                <div class="flex-shrink-0 ml-4">
                  <button
                    v-if="!isFriend(user.username)"
                    @click.stop="sendInvite(user.username)"
                    class="inline-flex items-center px-4 py-2 rounded-lg bg-green-500 text-white hover:bg-green-600 transition-colors"
                  >
                    <UserAddIcon class="h-4 w-4 mr-2" />
                    <span class="whitespace-nowrap">Add Friend</span>
                  </button>
                  <span
                    v-else
                    class="inline-flex items-center px-4 py-2 rounded-lg bg-green-100 text-green-700"
                  >
                    <CheckIcon class="h-4 w-4 mr-2" />
                    <span class="whitespace-nowrap">Friends</span>
                  </span>
                </div>
              </li>
            </transition-group>

            <!-- Empty State -->
            <div 
              v-if="filteredUsers.length === 0" 
              class="bg-white rounded-lg p-8 text-center shadow-sm"
            >
              <div class="text-gray-400">
                <UserXIcon class="h-12 w-12 mx-auto mb-2" />
                <p class="text-lg font-medium">No users found</p>
                <p class="text-sm">Try searching with a different username</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Initial State -->
        <div 
          v-else 
          class="bg-white rounded-lg p-8 text-center shadow-sm"
        >
          <div class="text-gray-400">
            <UsersIcon class="h-12 w-12 mx-auto mb-2" />
            <p class="text-lg font-medium">Search for friends</p>
            <p class="text-sm">Start typing to find people</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { 
  SearchIcon, 
  UserAddIcon, 
  CheckIcon, 
  UsersIcon,
  UserXIcon 
} from '@heroicons/vue/outline'

export default {
  components: {
    SearchIcon,
    UserAddIcon,
    CheckIcon,
    UsersIcon,
    UserXIcon
  },

  data() {
    return {
      searchQuery: '',
      isLoading: false,
      friends: [
        "JohnDoe",
        "JaneSmith",
        "CoolCoder123",
        "VueMaster",
      ],
      users: [
        { username: "JohnDoe" },
        { username: "JaneSmith" },
        { username: "CoolCoder123" },
        { username: "VueMaster" },
        { username: "NotYourFriend" },
        { username: "FutureFriend" },
        { username: "TestUser1" },
        { username: "TestUser2" },
        { username: "TestUser3" },
        { username: "TestUser4" },
      ]
    }
  },

  computed: {
    filteredUsers() {
      if (!this.searchQuery.trim()) {
        return []
      }
      return this.users.filter(user =>
        user.username.toLowerCase().includes(this.searchQuery.toLowerCase())
      )
    }
  },

  methods: {
    handleFocus() {
      // Add any focus handling if needed
    },

    isFriend(username) {
      return this.friends.includes(username)
    },

    async sendInvite(username) {
      this.isLoading = true
      await new Promise(resolve => setTimeout(resolve, 500))
      alert(`Friend request sent to ${username}!`)
      this.isLoading = false
    }
  },

  watch: {
    searchQuery(newVal) {
      if (newVal) {
        this.isLoading = true
        setTimeout(() => {
          this.isLoading = false
        }, 300)
      }
    }
  }
}
</script>

<style scoped>
/* Transitions for list items */
.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

/* Custom scrollbar */
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
  border-radius: 2px;
}

.overflow-y-auto:hover::-webkit-scrollbar-thumb {
  background-color: #A0AEC0;
}

/* Ensure text truncation works */
.truncate {
  max-width: 200px;
}
</style>