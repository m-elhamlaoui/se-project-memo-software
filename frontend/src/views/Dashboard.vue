<template>
  <CosmosBackground />

  <div class="flex">
        <SideBar/>

    <!-- Your other content goes here -->
    <router-view />
  </div>

</template>

<script>
import CosmosBackground from '../components/layout/CosmosBackground.vue';
import SideBar from '../components/layout/SideBar.vue';


export default {
  components: {
    CosmosBackground,SideBar
  }
};
</script>
