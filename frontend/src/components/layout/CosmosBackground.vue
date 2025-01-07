<template>
  <div>
    <canvas ref="cosmosCanvas"></canvas>
  </div>
</template>

<script>
export default {
  name: "CosmosBackground",
  mounted() {
    const canvas = this.$refs.cosmosCanvas;
    const ctx = canvas.getContext('2d');
    const particles = [];
    const numParticles = 100;
    // Resize canvas to fill the screen
    const resizeCanvas = () => {
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
    };

    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);

    class Particle {
      constructor(x, y, radius, speed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.angle = Math.random() * Math.PI * 2;
      }

      update() {
        this.x += Math.cos(this.angle) * this.speed;
        this.y += Math.sin(this.angle) * this.speed;

        // Bounce off edges
        if (this.x < 0 || this.x > canvas.width) this.angle = Math.PI - this.angle;
        if (this.y < 0 || this.y > canvas.height) this.angle = -this.angle;
      }

      draw() {
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
        ctx.fillStyle = 'rgba(0, 180, 0, 0.8)';
        ctx.fill();
      }
    }

    // Initialize particles
    for (let i = 0; i < numParticles; i++) {
      particles.push(
        new Particle(
          Math.random() * canvas.width,
          Math.random() * canvas.height,
          Math.random() * 4 + 1,
          Math.random() * 0.5 + 0.2
        )
      );
    }

    // Connect particles
    const connectParticles = () => {
      for (let a = 0; a < particles.length; a++) {
        for (let b = a + 1; b < particles.length; b++) {
          const dist = Math.hypot(
            particles[a].x - particles[b].x,
            particles[a].y - particles[b].y
          );

          if (dist < 120) {
            ctx.strokeStyle = `rgba(0, 0, 0, ${1 - dist / 120})`;
            ctx.lineWidth = 0.7;
            ctx.beginPath();
            ctx.moveTo(particles[a].x, particles[a].y);
            ctx.lineTo(particles[b].x, particles[b].y);
            ctx.stroke();
          }
        }
      }
    };

    // Animation Loop
    const animate = () => {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      particles.forEach(particle => {
        particle.update();
        particle.draw();
      });
      connectParticles();
      requestAnimationFrame(animate);
    };

    // Start Animation
    animate();
  }
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

canvas {
  display: block;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%; /* Ensure the width */
  height: 100%; /* Ensure canvas fully covers the height */
/*background: radial-gradient(circle, #1b2735, #090a0f);*/
z-index: -1;

}
</style>
