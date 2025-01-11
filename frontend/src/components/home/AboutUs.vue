<template>
  <div
    ref="taskBlockApp"
    id="task-block-app"
    class="min-h-screen w-full opacity-0 transform translate-y-4 transition-all duration-700"
  >
    <div class="max-w-7xl mx-auto px-4 py-20 relative">
      <!-- Background Elements -->
      <div class="absolute top-0 right-0 w-96 h-96 bg-gradient-to-br from-green-500/10 to-transparent rounded-full filter blur-3xl"></div>
      <div class="absolute bottom-0 left-0 w-96 h-96 bg-gradient-to-tr from-green-500/10 to-transparent rounded-full filter blur-3xl"></div>

      <!-- Hero Section -->
      <div class="relative z-10 space-y-24">
        <!-- Title Section -->
        <div class="text-center space-y-8">
          <!-- Main Titles -->
          <div class="space-y-4">
            <h1 class="text-7xl sm:text-8xl md:text-9xl font-sans leading-tight tracking-widest text-green-500">EASY TO USE</h1>
<h1 class="text-7xl sm:text-8xl md:text-9xl font-sans leading-tight tracking-widest text-green-500">Task Manager</h1>
          </div>

          <!-- Decorative Lines -->
          <div class="flex justify-center items-center gap-4">
            <div class="h-px w-24 bg-gradient-to-r from-transparent via-green-500 to-transparent"></div>
            <div class="w-3 h-3 rounded-full bg-green-500 animate-pulse"></div>
            <div class="h-px w-24 bg-gradient-to-l from-transparent via-green-500 to-transparent"></div>
          </div>
        </div>

        <!-- Content Section -->
        <div class="neo-glass-container p-12 rounded-3xl relative overflow-hidden">
          <!-- Background Pattern -->
          <div class="absolute inset-0 opacity-5">
            <div class="absolute inset-0 bg-gradient-to-r from-green-500 to-transparent"></div>
            <div class="grid grid-cols-8 h-full">
              <div v-for="n in 8" :key="n" class="border-l border-green-500/10"></div>
            </div>
          </div>

          <div class="relative z-10 flex flex-col lg:flex-row items-center gap-16">
            <!-- Image Section -->
            <div class="w-full lg:w-1/2">
              <div class="relative group">
                <!-- Image Container -->
                <div class="relative z-10 rounded-2xl overflow-hidden">
                  <img 
                    :src="require('../../assets/image.png')"
                    alt="Web3 Tools Illustration"
                    class="w-full aspect-square object-cover transform transition-transform duration-700 group-hover:scale-110"
                  >
                  <div class="absolute inset-0 bg-gradient-to-t from-black/20 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-700"></div>
                </div>

                <!-- Decorative Elements -->
                <div class="absolute -inset-4 bg-gradient-to-r from-green-500/30 to-green-300/30 rounded-2xl blur-2xl opacity-0 group-hover:opacity-100 transition-all duration-700 z-0"></div>
                <div class="absolute top-0 right-0 w-20 h-20 border-t-2 border-r-2 border-green-500/30 rounded-tr-2xl"></div>
                <div class="absolute bottom-0 left-0 w-20 h-20 border-b-2 border-l-2 border-green-500/30 rounded-bl-2xl"></div>
              </div>
            </div>

            <!-- Content -->
            <div class="flex-1 space-y-8">
              <div class="prose prose-lg">
                <p class="text-xl leading-relaxed text-gray-700">
                  Our Task Manager uses 
                  <span class="highlight-text">Blockchain</span> for secure,
                  <span class="highlight-text">transparent</span>, and 
                  <span class="highlight-text">decentralized</span> task tracking.
                </p>
                <p class="text-xl leading-relaxed text-gray-700">
                  With <span class="highlight-text">WEB3</span> integration, 
                  your tasks are verifiable and immutable, ensuring privacy and trust in a 
                  decentralized environment.
                </p>
              </div>

              <!-- Tech Decorations -->
              <div class="flex items-center gap-3">
                <div v-for="n in 3" :key="n" 
                     class="tech-line h-0.5" 
                     :class="'w-' + (24 - n * 6)"
                     :style="{ animationDelay: n * 200 + 'ms' }">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TaskBlockApp',
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
        threshold: 0.2,
        rootMargin: '50px'
      }

      this.observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('opacity-100', 'translate-y-0')
            this.observer.unobserve(entry.target)
          }
        })
      }, options)

      if (this.$refs.taskBlockApp) {
        this.observer.observe(this.$refs.taskBlockApp)
      }
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Anton&family=Roboto:wght@400;500;700&display=swap');

/* Hero Title */
.hero-title {
  font-family: 'Anton', sans-serif;
  background: linear-gradient(180deg, #22C55E 0%, #15803D 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  position: relative;
  display: inline-block;
  letter-spacing: 0.02em;
  filter: drop-shadow(0 4px 8px rgba(34, 197, 94, 0.3));
}

/* Container Styles */
.neo-glass-container {
  background: rgba(255, 255, 255, 0.95);
  border: 1px solid rgba(34, 197, 94, 0.1);
  box-shadow: 
    0 8px 32px -4px rgba(34, 197, 94, 0.1),
    0 4px 16px -2px rgba(34, 197, 94, 0.06);
  backdrop-filter: blur(20px);
}

/* Highlight Text */
.highlight-text {
  color: #15803D;
  font-weight: 600;
  position: relative;
  display: inline-block;
}

.highlight-text::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, #22C55E, #15803D);
  transform: scaleX(0);
  transform-origin: right;
  transition: transform 0.5s ease;
}

.highlight-text:hover::after {
  transform: scaleX(1);
  transform-origin: left;
}

/* Tech Line Animation */
.tech-line {
  background: linear-gradient(90deg, #22C55E, transparent);
  border-radius: 9999px;
  animation: pulse 3s infinite;
}

@keyframes pulse {
  0%, 100% {
    opacity: 0.5;
    transform: scaleX(0.98);
  }
  50% {
    opacity: 0.8;
    transform: scaleX(1);
  }
}

/* Responsive Design */
@media (max-width: 768px) {
  .hero-title {
    font-size: clamp(3rem, 10vw, 5rem);
  }
}
</style>