<template>
  <div>
    <header class="flex items-center p-6 bg-white shadow-md font-roboto">
      <h1 class="text-2xl font-bold text-green-500 mr-20">Home</h1>
      <div class="bg-white rounded-xl p-4 shadow-md border border-gray-200 flex justify-center w-4/5">
        <ul class="flex gap-x-8 text-gray-600">
          <li class="text-lg font-medium hover:underline transition duration-200 ">
            <a href="#" @click="scrollToInfo" >What is TaskBlock?</a>
          </li>
          
          <li class="text-lg font-medium hover:text-gray-800 hover:underline transition duration-200">
            <a href="#" @click="scrollToWallet">Wallet</a>
          </li>

          <li class="text-lg font-medium hover:text-gray-800 hover:underline transition duration-200">
           <a href="#" @click="scrollToVoting">Our voting system</a> 
          </li>

          
          <li class="text-lg font-medium hover:text-gray-800 hover:underline transition duration-200">
            More
          </li>
        </ul>
      </div>
    </header>

    <!-- Sections with existing ids to scroll to -->
    
  </div>
</template>

<script>
export default {
  methods: {
    scrollToWallet() {
      const element = document.getElementById('wallet-block-app');
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    },
    scrollToInfo() {
      const element = document.getElementById('task-block-app');
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    },
    handleScroll() {
      const sections = document.querySelectorAll("section");
      sections.forEach((section) => {
        const rect = section.getBoundingClientRect();
        if (rect.top < window.innerHeight * 0.8) {
          section.classList.add("opacity-100", "translate-y-0");
        }
      });
    } , 
    scrollToVoting() {
      const element = document.getElementById('voting-system-section');
      if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
      }
    },
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
    this.handleScroll(); // Check initially in case the sections are already in view
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.handleScroll);
  }
};
</script>

<style scoped>
/* Hover effect on navigation items */
li:hover {
  color: #7FFF00; /* Change text color to chartreuse (light green) on hover */
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
