<template>
  <div
    ref="votingSection"
    class="max-w-2xl mx-auto p-8 bg-gradient-to-br from-green-100 to-green-50 rounded-3xl shadow-xl mb-20 opacity-0 transform translate-y-4 transition-all duration-700"
    id="voting-system-section"
  >
<h1 class="text-6xl sm:text-7xl font-sans leading-tight text-center mb-6 tracking-widest text-green-500">
  Voting System Ready-to-Use
</h1>
    <p class="text-xl text-gray-700 mb-4">
      Check if you have enough tokens in your <span class="highlight">Aura</span> wallet, and start voting right away.
    </p>
    <p class="text-xl text-gray-700">
      Use blockchain technology to cast secure votes, from task prioritization to ensuring transparent results in your task manager.
    </p>
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
    this.setupIntersectionObserver();
  },
  beforeUnmount() {
    if (this.observer) {
      this.observer.disconnect();
    }
  },
  methods: {
    setupIntersectionObserver() {
      const options = {
        threshold: 0.2 // Trigger when 20% of the element is visible
      };

      this.observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
          if (entry.isIntersecting) {
            entry.target.classList.add('opacity-100', 'translate-y-0');
            // Stop observing after animation
            this.observer.unobserve(entry.target);
          }
        });
      }, options);

      if (this.$refs.votingSection) {
        this.observer.observe(this.$refs.votingSection);
      }
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Anton&family=Roboto:wght@400;500;700&display=swap');

.hero-title {
  font-family: 'Anton', sans-serif;
  background: linear-gradient(135deg, #22C55E 0%, #15803D 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: 0.02em;
  filter: drop-shadow(0 4px 8px rgba(34, 197, 94, 0.3));
  line-height: 1.2;
}

.highlight {
  color: #15803D;
  font-weight: 600;
  position: relative;
  display: inline-block;
}

#voting-system-section {
  background: rgba(255, 255, 255, 0.9);
  border: 1px solid rgba(34, 197, 94, 0.1);
  box-shadow: 
    0 8px 32px -4px rgba(34, 197, 94, 0.1),
    0 4px 16px -2px rgba(34, 197, 94, 0.06);
  backdrop-filter: blur(20px);
  transform: translateY(4rem);
  transition: opacity 0.7s ease-out, transform 0.7s ease-out;
  border-radius: 1.5rem;
}

#voting-system-section.opacity-100 {
  transform: translateY(0);
}

p {
  font-family: 'Roboto', sans-serif;
  font-size: 1.25rem; /* Matches the text-xl class */
  line-height: 1.75rem;
  color: #4B5563;
}

.highlight {
  color: #22C55E;
  font-weight: 700;
  position: relative;
}

p span.highlight {
  text-decoration: underline;
}
</style>
