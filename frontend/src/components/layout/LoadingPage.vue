<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <!-- Cube Container -->
    <div class="relative w-20 h-20 cube">
      <!-- Cube Faces -->
      <div
        class="absolute w-full h-full bg-green-500 bg-opacity-75 cube-face transform"
        v-for="(face, index) in faces"
        :key="index"
        :style="face.style"
      ></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SpinningCube",
  data() {
    return {
      faces: [
        { style: "transform: rotateY(0deg) translateZ(40px);" },
        { style: "transform: rotateY(90deg) translateZ(40px);" },
        { style: "transform: rotateY(180deg) translateZ(40px);" },
        { style: "transform: rotateY(-90deg) translateZ(40px);" },
        { style: "transform: rotateX(90deg) translateZ(40px);" },
        { style: "transform: rotateX(-90deg) translateZ(40px);" },
      ],
    };
  },
};
</script>

<style scoped>
/* Keyframes for spinning the cube */
@keyframes spin {
  0% {
    transform: rotateX(0) rotateY(0);
  }
  100% {
    transform: rotateX(360deg) rotateY(360deg);
  }
}

/* Cube container style */
.cube {
  perspective: 1000px;
  transform-style: preserve-3d;
  animation: spin 4s infinite linear;
}

/* Cube faces */
.cube-face {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  border: 2px solid rgba(255, 255, 255, 0.8); /* Clearer edges */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* Shadow for depth */
}


</style>
