<template>
  <div class="discussion-wrapper w-full">
    <div class="discussion-history ">
      <div class="friends-list-container ">
        <!-- Header -->
        <div class="flex items-center justify-between mb-6">
          <h2 class="text-xl font-bold text-gray-800">Invitations</h2>
          <span class="px-3 py-1 bg-green-100 text-green-700 rounded-full text-sm font-medium">
            {{ invitations.length }} invitations
          </span>
        </div>

        <!-- Friends List -->
        <TransitionGroup 
          name="list" 
          tag="ul" 
          class="space-y-3"
        >
          <li
            v-for="friend in invitations"
            :key="friend.username"
            class="bg-white rounded-lg p-4 shadow-sm flex items-center justify-between transform hover:scale-[1.01] transition-all duration-200"
          >
            <!-- Friend Info -->
            <div class="flex items-center space-x-3">
              <!-- Avatar -->
              <div class="h-10 w-10 rounded-full bg-gradient-to-br from-green-100 to-green-200 flex items-center justify-center flex-shrink-0">
                <span class="text-green-600 font-medium">
                  {{ friend.username.charAt(0).toUpperCase() }}
                </span>
              </div>
              <!-- Username -->
              <div class="flex flex-col">
                <span class="font-medium text-gray-900">{{ friend.username }}</span>
              </div>
            </div>

            <!-- Actions -->
            <div class="flex items-center space-x-2">
              <button
                @click="Accept(friend.id)"
                class="inline-flex items-center px-3 py-2 rounded-lg text-sm font-medium text-green-600 hover:bg-green-50 transition-colors"
              >
                Accept
              </button>

              <button
                @click="Reject(friend.id)"
                class="inline-flex items-center px-3 py-2 rounded-lg text-sm font-medium text-red-600 hover:bg-red-50 transition-colors"
              >
                Remove
              </button>
            </div>
          </li>
        </TransitionGroup>

        <!-- Empty State -->
        <div 
          v-if="!invitations.length" 
          class="text-center py-12 bg-white rounded-lg shadow-sm"
        >
          <div class="w-16 h-16 mx-auto mb-4 rounded-full bg-gray-100 flex items-center justify-center">
            <UserIcon class="h-8 w-8 text-gray-400" />
          </div>
          <h3 class="text-lg font-medium text-gray-900 mb-1">No Invitations Yet</h3>
          <p class="text-gray-500">Start adding friends </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { UserIcon } from '@heroicons/vue/outline'
import { mapActions ,mapState} from 'vuex';

export default {
  components: {
    UserIcon
  },

  data() {
    return {
    }
  },
  computed: {
    invitations() {
      if (this.$store.state.auth.user) return this.$store.state.auth.user.inviters.map(user => ({
        username: user.handle,id:user.id
    }))
    return []
    }
  },
  methods: {
    ...mapActions('member', ['accept','reject']),

    async Accept(id){
      await this.accept({id})

    },
    async Reject(id){
      await this.reject({id})

    }
  },
}
</script>

<style scoped>
.discussion-wrapper {
  @apply h-screen flex flex-col bg-gray-50;
}

.discussion-history {
  @apply flex-1 overflow-y-auto p-4;
}

.friends-list-container {
  @apply bg-white/80 backdrop-blur-lg rounded-xl p-6  mx-auto;
}

/* List Transitions */
.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}

/* Custom scrollbar */
.discussion-history {
  scrollbar-width: thin;
  scrollbar-color: #CBD5E0 transparent;
}

.discussion-history::-webkit-scrollbar {
  width: 4px;
}

.discussion-history::-webkit-scrollbar-track {
  background: transparent;
}

.discussion-history::-webkit-scrollbar-thumb {
  background-color: #CBD5E0;
  border-radius: 2px;
}

.discussion-history:hover::-webkit-scrollbar-thumb {
  background-color: #A0AEC0;
}
</style>