<template>
  <div class="flex">
    <CosmosBackground />
    <!-- Your other content goes here -->
    <SideBar/>
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
