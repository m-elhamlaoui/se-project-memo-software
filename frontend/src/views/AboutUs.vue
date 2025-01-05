<template>
  <div
    id="task-block-app"
    class="bg-white flex flex-col items-center justify-center p-6 opacity-0 transform translate-y-4 transition-all duration-700"
  >
    <!-- Main Titles -->
    <h1 class="text-6xl font-extrabold text-transparent bg-clip-text bg-gradient-to-b from-green-500 to-green-900 uppercase leading-tight custom-font">
      EASY TO USE
    </h1>
    <h1 class="text-6xl font-extrabold text-transparent bg-clip-text bg-gradient-to-b from-green-500 to-green-900 uppercase leading-tight custom-font">
      WEB3 TOOLS.
    </h1>

    <div class="flex items-center justify-evenly w-full">
      <!-- Image Section -->
      <img src="../assets/image.png" class="h-64 w-64">

      <!-- Description Section -->
      <p class="text-lg text-gray-800 text-right max-w-2xl px-4 par">
        Our Task Manager uses blockchain for secure, transparent, and decentralized task tracking. With Web3 integration, your tasks are verifiable and immutable, ensuring privacy and trust in a decentralized environment.
      </p>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    handleScroll() {
      const taskBlockApp = document.getElementById('task-block-app');
      const rect = taskBlockApp.getBoundingClientRect();
      if (rect.top < window.innerHeight * 0.8) {
        taskBlockApp.classList.add('opacity-100', 'translate-y-0');
      }
    }
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
    this.handleScroll(); // Check initially in case the section is already in view
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.handleScroll);
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Anton&family=Roboto:wght@400;500&display=swap');

.par {
  font-family: 'Roboto';
}

/* Apply the custom font to the h1 */
.custom-font {
  font-family: 'Anton', sans-serif;
  font-size: 100px;
}

/* AOT effect for smooth animations */
#task-block-app {
  opacity: 0;
  transform: translateY(4rem);
  transition: opacity 0.7s ease-out, transform 0.7s ease-out;
}

#task-block-app.opacity-100 {
  opacity: 1;
  transform: translateY(0);
}
</style>
