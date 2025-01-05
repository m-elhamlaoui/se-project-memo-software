<template>
  <div
    id="wallet-block-app"
    class="bg-[#0c4c29] p-10 rounded-3xl max-w-3xl mx-auto flex justify-evenly container opacity-0 transform translate-y-4 transition-all duration-700"
  >
    <div class="flex-1 par">
      <!-- Header Section -->
      <div>
        <h1 class="text-5xl font-bold text-[#7FFF00] custom-font">
          A WALLET FOR EVERY PHONE NUMBER
        </h1>
        <p class="text-lg text-gray-300 mt-5">
          SukuPay enables global money transfers between phone numbers – no apps or complex setups required. Fully non-custodial. You own your money.
        </p>
        <div class="text-center mt-12">
          <button class="bg-[#7FFF00] text-white py-3 px-8 rounded-full text-lg hover:bg-[#64e000] transition duration-300">
            Try it now
          </button>
        </div>
      </div>
    </div>

    <div class="flex-1 flex justify-center items-center">
      <img class="w-30 h-30 rounded-full" src="../assets/file.png" alt="User" />
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    handleScroll() {
      const walletBlock = document.getElementById('wallet-block-app');
      const rect = walletBlock.getBoundingClientRect();
      if (rect.top < window.innerHeight * 0.8) {
        walletBlock.classList.add('opacity-100', 'translate-y-0');
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
/* Hover effect on navigation items */
a:hover {
  color: #7FFF00; /* Change text color to chartreuse (light green) on hover */
}

/* Custom font */
.par {
  font: 'Roboto', sans-serif;
}

/* Apply the custom font to the h1 */
.custom-font {
  font-family: 'Anton', sans-serif;
}

.container {
  margin-bottom: 100px;
}

/* AOT effect for smooth animations */
#wallet-block-app {
  opacity: 0;
  transform: translateY(4rem);
  transition: opacity 0.7s ease-out, transform 0.7s ease-out;
}

#wallet-block-app.opacity-100 {
  opacity: 1;
  transform: translateY(0);
}
</style>
