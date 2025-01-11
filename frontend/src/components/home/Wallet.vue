<template>
  <div
    ref="walletBlock"
    id="wallet-block-app"
    class="relative max-w-6xl mx-auto opacity-0 transform translate-y-4 transition-all duration-700"
  >
    <!-- Background Elements -->
    <div class="absolute inset-0 neo-glass-container rounded-3xl">
      <div class="absolute inset-0 bg-gradient-radial from-green-500/10 via-green-500/5 to-transparent"></div>
      <div class="grid grid-cols-12 h-full opacity-10">
        <div v-for="n in 12" :key="n" class="border-l border-green-500/20"></div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="relative p-12 flex flex-col lg:flex-row items-center gap-16">
      <!-- Left Content Section -->
      <div class="flex-1 space-y-10">
        <!-- Header Section with animated line -->
        <div class="relative space-y-2">
          <h1 class="hero-title text-6xl sm:text-7xl font-anton leading-tight">
            A WALLET FOR EVERY
            <br />
            MEMBER
          </h1>
          <div class="tech-line"></div>
        </div>

        <!-- Description -->
        <p class="text-xl text-gray-700 font-roboto leading-relaxed">
          You have your own aura wallet  , 
          <span class="highlight">no apps</span> or complex setups required. 
          <span class="highlight">Don't let your wallet reach zero</span>.
        </p>

        <!-- Button Container -->
        <div class="flex justify-start">
          <button class="neo-button group relative overflow-hidden">
            <span class="relative z-10 text-lg font-medium" @click="this.$router.push('/login')">
              Try it now
            </span>
            <div class="absolute inset-0 bg-gradient-to-r from-green-500 to-green-600 opacity-0 group-hover:opacity-100 transition-opacity duration-300"></div>
          </button>
        </div>

        <!-- Decorative Elements -->
        <div class="flex items-center gap-3">
          <div class="tech-dot"></div>
          <div class="h-px flex-grow bg-gradient-to-r from-green-500 to-transparent"></div>
        </div>
      </div>

      <!-- Right Image Section -->
      <div class="relative w-full lg:w-1/2 aspect-square">
        <div class="relative group">
          <!-- Main Image Container -->
          <div class="relative z-10 rounded-full overflow-hidden shadow-xl">
            <img 
              :src="require('../../assets/file.png')"
              alt="Wallet Interface"
              class="w-full h-full object-cover transform transition-transform duration-700 group-hover:scale-110"
            />
            
            <!-- Image Overlay -->
            <div class="absolute inset-0 bg-gradient-to-t from-green-500/20 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-700"></div>
          </div>

          <!-- Background Glow Effect -->
          <div class="absolute -inset-4 bg-gradient-to-r from-green-500/20 to-green-300/20 rounded-full blur-2xl opacity-0 group-hover:opacity-100 transition-all duration-700 z-0"></div>

          <!-- Decorative Dots -->
          <div class="absolute -top-4 -right-4 w-8 h-8">
            <div class="tech-dot animate-float"></div>
          </div>
          <div class="absolute -bottom-4 -left-4 w-8 h-8">
            <div class="tech-dot animate-float delay-150"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      observer: null
    }
  },
  mounted() {
    this.setupIntersectionObserver()
  },
  beforeUnmount() {
    if (this.observer) {
      this.observer.disconnect()
    }
  },
  methods: {
    setupIntersectionObserver() {
      const options = {
        threshold: 0.2
      }

      this.observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('opacity-100', 'translate-y-0')
            this.observer.unobserve(entry.target)
          }
        })
      }, options)

      if (this.$refs.walletBlock) {
        this.observer.observe(this.$refs.walletBlock)
      }
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Anton&family=Roboto:wght@400;500;700&display=swap');

.hero-title {
  background: linear-gradient(135deg, #22C55E 0%, #15803D 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: 0.02em;
  filter: drop-shadow(0 4px 8px rgba(34, 197, 94, 0.3));
}

.neo-glass-container {
  background: rgba(255, 255, 255, 0.95);
  border: 1px solid rgba(34, 197, 94, 0.1);
  box-shadow: 
    0 8px 32px -4px rgba(34, 197, 94, 0.1),
    0 4px 16px -2px rgba(34, 197, 94, 0.06);
  backdrop-filter: blur(20px);
}

.neo-button {
  padding: 1rem 2.5rem;
  border-radius: 9999px;
  background: white;
  border: 1px solid rgba(34, 197, 94, 0.2);
  color: #15803D;
  box-shadow: 
    0 4px 12px rgba(34, 197, 94, 0.1),
    0 2px 4px rgba(34, 197, 94, 0.06);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.neo-button:hover {
  transform: translateY(-2px);
  color: rgb(21, 1, 1);
  box-shadow: 
    0 6px 16px rgba(34, 197, 94, 0.15),
    0 3px 6px rgba(34, 197, 94, 0.1);
}

.highlight {
  color: #15803D;
  font-weight: 600;
  position: relative;
  display: inline-block;
}

.tech-line {
  height: 2px;
  width: 60%;
  background: linear-gradient(90deg,
    rgba(34, 197, 94, 0.8),
    rgba(34, 197, 94, 0.1)
  );
  margin-top: 1rem;
}

.tech-dot {
  width: 8px;
  height: 8px;
  background: #22C55E;
  border-radius: 50%;
  box-shadow: 0 0 16px rgba(34, 197, 94, 0.6);
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

.animate-float {
  animation: float 3s ease-in-out infinite;
}

.delay-150 {
  animation-delay: 150ms;
}

@media (max-width: 768px) {
  .hero-title {
    font-size: clamp(2.5rem, 8vw, 4rem);
    line-height: 1.2;
  }
}
</style>