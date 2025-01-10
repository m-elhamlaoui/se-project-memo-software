<template>
  <div class="layout-container">
    <CosmosBackground style="height: 100%;" />
    <div class="content-container">
      <div class="w-full">
        <Home v-if="!isMobileView" class="flex justify-center items-center w-full" />
        <AboutUs class="flex justify-center items-center w-full" />
        <Wallet class="flex justify-center items-center w-full" />
        <div class="flex justify-center items-center w-full mt-12">
          <VotingSystem />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';
import Home from '../components/home/Home.vue';
import AboutUs from '../components/home/AboutUs.vue';
import Wallet from '../components/home/Wallet.vue';
import VotingSystem from '../components/home/VotingSystem.vue';
import SidebarHome from '@/components/home/SidebarHome.vue';
import SideBar from '@/components/layout/SideBar.vue';
import CosmosBackground from '../components/layout/CosmosBackground.vue';

export default {
  components: {
    Home,
    AboutUs,
    Wallet,
    VotingSystem,
    SidebarHome,
    SideBar,
    CosmosBackground
  },
  setup() {
    const isMobileView = ref(window.innerWidth <= 800);

    const updateView = () => {
      isMobileView.value = window.innerWidth <= 800;
    };

    onMounted(() => {
      window.addEventListener('resize', updateView);
    });

    onUnmounted(() => {
      window.removeEventListener('resize', updateView);
    });

    return {
      isMobileView,
    };
  },
};
</script>

<style scoped>
.layout-container {
  display: flex;
  flex-direction: row;
  width: 100%;
}

.sidebar {
  position: fixed;
  height: 100%;
}

.content-container {
  flex: 1;
  margin-left: 0;
  padding: 20px;
  display: flex;
  flex-direction: column;
  width: 100%;
  transition: margin-left 0.3s ease;
}

@media screen and (max-width: 800px) {
  .content-container {
    margin-left: 250px;
  }
}
</style>