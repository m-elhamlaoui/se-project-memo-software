<template>

 <!--


 <div class="flex">
<div        :class="[isCollapsed ? 'w-16' : 'w-64',' transition-all top-0 left-0 h-screen m-0 flex flex-col   bg-gray-900 text-white shadow-lg']"
                    >
                    
                    <div className="sidebar-icon group">
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M20.25 6.375c0 2.278-3.694 4.125-8.25 4.125S3.75 8.653 3.75 6.375m16.5 0c0-2.278-3.694-4.125-8.25-4.125S3.75 4.097 3.75 6.375m16.5 0v11.25c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125V6.375m16.5 0v3.75m-16.5-3.75v3.75m16.5 0v3.75C20.25 16.153 16.556 18 12 18s-8.25-1.847-8.25-4.125v-3.75m16.5 0c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125" />
                  </svg>
                  <span class="sidebar-tooltip group-hover:scale-100">{text}</span>
                </div>
                <div className="sidebar-icon group">
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M20.25 6.375c0 2.278-3.694 4.125-8.25 4.125S3.75 8.653 3.75 6.375m16.5 0c0-2.278-3.694-4.125-8.25-4.125S3.75 4.097 3.75 6.375m16.5 0v11.25c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125V6.375m16.5 0v3.75m-16.5-3.75v3.75m16.5 0v3.75C20.25 16.153 16.556 18 12 18s-8.25-1.847-8.25-4.125v-3.75m16.5 0c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125" />
                  </svg>
                  <span class="sidebar-tooltip group-hover:scale-100">{text}</span>
                </div>
                <div className="sidebar-icon group">
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M20.25 6.375c0 2.278-3.694 4.125-8.25 4.125S3.75 8.653 3.75 6.375m16.5 0c0-2.278-3.694-4.125-8.25-4.125S3.75 4.097 3.75 6.375m16.5 0v11.25c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125V6.375m16.5 0v3.75m-16.5-3.75v3.75m16.5 0v3.75C20.25 16.153 16.556 18 12 18s-8.25-1.847-8.25-4.125v-3.75m16.5 0c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125" />
                  </svg>
                  <span class="sidebar-tooltip group-hover:scale-100">{text}</span>
                </div>



    </div>


  <button @click="isCollapsed=!isCollapsed">oei</button>
  

  <StarIcon class="w-64 h-64 text-yellow-500" />



</div>





 --> 

 <router-view />
</template>

<script>
import { mapActions ,mapState} from 'vuex';
import { StarIcon } from '@heroicons/vue/solid';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css';
import WebSocketService from '@/services/WebSocketService'

export default {
  data(){
    return{
      isCollapsed:true
    }
  },
  methods:{
    ...mapActions('auth', ['setupUserSocket']),

  },

  async created(){
    // here you will associate all the websockets
    await WebSocketService.connect()
    if(this.$store.state.auth.user.handle){
      await this.setupUserSocket()
    }
  }
  
}
</script>

<style>

</style>


