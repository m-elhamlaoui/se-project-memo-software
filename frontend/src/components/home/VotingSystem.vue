<template>
    <div
      class="max-w-2xl mx-auto p-8 bg-green-50 rounded-xl shadow-lg mb-20 opacity-0 transform translate-y-4 transition-all duration-700"
      id="voting-system-section"
    >
      <h1 class="text-4xl font-bold text-black-700 text-center mb-6 custom-font">
        Voting System Ready-to-Use
      </h1>
      <p class="text-lg text-gray-700 mb-4 par">
        Download the extension, log in with your <span class="font-bold text-neoGreen-700">Web3 </span> wallet, and start voting right away.
      </p>
      <p class="text-lg text-gray-700 par">
        You can use blockchain technology to cast secure votes, from task prioritization to ensuring transparent results in your task manager.
      </p>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      handleScroll() {
        const votingSystemSection = document.getElementById('voting-system-section');
        const rect = votingSystemSection.getBoundingClientRect();
        if (rect.top < window.innerHeight * 0.8) {
          votingSystemSection.classList.add('opacity-100', 'translate-y-0');
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
  /* Import the Anton font from Google Fonts */
  @import url('https://fonts.googleapis.com/css2?family=Anton&family=Roboto:wght@400;500&display=swap');
  
  /* Apply the custom font to the h1 */
  .custom-font {
    font-family: 'Anton', sans-serif;
  }
  
  span {
    color: #7FFF00;
  }
  
  .par {
    font-family: 'Roboto', sans-serif;
  }
  
  /* AOT effect for smooth animations */
  #voting-system-section {
    opacity: 0;
    transform: translateY(4rem);
    transition: opacity 0.7s ease-out, transform 0.7s ease-out;
  }
  
  #voting-system-section.opacity-100 {
    opacity: 1;
    transform: translateY(0);
  }
  </style>
  