<template>
  <div>
    <header class="flex items-center p-6 bg-white shadow-md font-roboto">
      <h1 class="text-2xl font-bold text-green-500 mr-20">Home</h1>
      <div class="bg-white rounded-xl p-4 shadow-md border border-gray-200 flex justify-center w-4/5">
        <ul class="flex gap-x-8 text-gray-600">
          <li class="text-lg font-medium hover:underline transition duration-200">
            <a href="#task-block-app" @click.prevent="scrollToSection('task-block-app')">
              What is TaskBlock?
            </a>
          </li>

          <li class="text-lg font-medium hover:text-gray-800 hover:underline transition duration-200">
            <a href="#wallet-block-app" @click.prevent="scrollToSection('wallet-block-app')">
              Wallet
            </a>
          </li>

          <li class="text-lg font-medium hover:text-gray-800 hover:underline transition duration-200">
            <a href="#voting-system-section" @click.prevent="scrollToSection('voting-system-section')">
              Our voting system
            </a>
          </li>

          <li class="text-lg font-medium hover:text-gray-800 hover:underline transition duration-200">
            <a href="#" @click.prevent="navigateToLogin">Login</a>
          </li>
        </ul>
      </div>
    </header>
  </div>
</template>

<script>
export default {
  methods: {
    scrollToSection(sectionId) {
      // Only attempt to scroll if we're on the home page
      if (this.$route.path === '/') {
        const element = document.getElementById(sectionId);
        if (element) {
          element.scrollIntoView({ behavior: 'smooth' });
        }
      } else {
        // If we're not on home page, navigate there first
        this.$router.push('/').then(() => {
          this.$nextTick(() => {
            const element = document.getElementById(sectionId);
            if (element) {
              element.scrollIntoView({ behavior: 'smooth' });
            }
          });
        });
      }
    },
    
    navigateToLogin() {
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
/* Hover effect on navigation items */
li:hover {
  color: #7FFF00;
  cursor: pointer;
}

/* Custom font */
.font-roboto {
  font-family: 'Roboto', sans-serif;
}

/* Smooth scrolling setup for all browsers */
html {
  scroll-behavior: smooth;
}
</style>