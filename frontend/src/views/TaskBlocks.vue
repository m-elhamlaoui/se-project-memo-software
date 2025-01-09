<template>
  <div class=" wrapper-container">
     <div class="welcome-message">
      <div class="text-title" ref="welcomeText"></div>
    </div>
      <div class=" taskblock-container rounded-xl">
       <taskblockEmpty v-if="wallets.length === 0"/>

       <taskblockCard :key="index"
        v-for="index,wallet in wallets" :title="wallet.name" />
       
      </div>
  </div>
</template>

<script>
import taskblockCard from '@/components/taskblocks/taskblockCard.vue'
import taskblockEmpty from '@/components/taskblocks/taskblockEmpty.vue'
import { mapActions ,mapState} from 'vuex';


export default {
  components: { taskblockCard ,taskblockEmpty},
  data(){
    return({
        handle: this.$store.state.auth.user.handle,
        wallets:[]
    })
  },
  async mounted() {

    
    const message = `Welcome, ${this.handle}!`;
    const welcomeText = this.$refs.welcomeText;
    let i = 0;

    // Typing animation
    const typeWriter = () => {
      if (i < message.length) {
        welcomeText.innerHTML += message.charAt(i);
        i++;
        setTimeout(typeWriter, 100); // Adjust typing speed (100ms per character)
      }
    };

    typeWriter();

    const temp = await this.getTaskblocks(this.$store.state.auth.user.id)
    this.wallets = temp
  },
  methods:{
    ...mapActions('taskblocks', ['getTaskblocks']),
  }
}
</script>

<style scoped>
.taskblock-container {
     background: rgba(0, 0, 0, 0.01); /* Semi-transparent background */
  backdrop-filter: blur(10px); /* Blurs the content behind the container */
    display: flex;
    width: 100%;
  flex-wrap: wrap;
  justify-content: space-between;
  gap: 16px;
  overflow-y: auto; /* Enables vertical scrolling when the content exceeds the max height */
  padding-right: 10px; /* Optional: prevents the content from being hidden under the scrollbar */
  margin-right: 10px;
  margin-left: 10px;

}

.wrapper-container{
  max-height: 100vh;
       display: flex;
width: 100%;
flex-direction: column;
  gap: 5px;
  max-height: 100vh; /* Limiting the container height to 80% of the viewport */
  overflow-y: auto; /* Enables vertical scrolling when the content exceeds the max height */
  padding-right: 10px; /* Optional: prevents the content from be*/
}

/* Hide the scrollbar by default */
.taskblock-container::-webkit-scrollbar {
  width: 8px;
  opacity: 0; /* Initially hide the scrollbar */
}

/* Show the scrollbar when the container is hovered */
.taskblock-container:hover::-webkit-scrollbar {
  opacity: 1;
}

/* Style the scrollbar track */
.taskblock-container::-webkit-scrollbar-track {
  background-color: transparent;
}

/* Style the scrollbar thumb (the draggable part) */
.taskblock-container::-webkit-scrollbar-thumb {
  border-radius: 10px;
  transition: background-color 0.3s ease;
}

/* Change the scrollbar thumb color when hovered */
.taskblock-container:hover::-webkit-scrollbar-thumb {
  background-color: #555; /* Darker gray when hovered */
}

.taskblock-container > * {
  flex: 1 1 calc(33.33% - 16px);
  min-width: 250px;
  box-sizing: border-box;
}

/* Wrapper for the welcome message */
.welcome-message {
  display: flex;
  justify-content: center;
  align-content: center;
  text-align: center;
    width: 100%;
     background: linear-gradient(45deg, rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.3));
  background-size: 400% 400%; /* This makes the gradient larger than the container */
  animation: moveGradient 5s linear infinite; /* Animation for the gradient */
  backdrop-filter: blur(10px); /* Blurs the content behind the container */

    height: 13vh;
  margin-bottom: 20px;
  text-align: left;
  font-size: 2rem;
  font-weight: bold;
  color: #4caf50; /* Optional: Customize color */
}
@keyframes moveGradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

/* Typing effect cursor (optional) */
.welcome-message h1::after {
  content: "|";
  animation: blink 1s step-start infinite;
  color: #4caf50; /* Same as text color */
}

@keyframes blink {
  50% {
    opacity: 0;
  }
}
.text-title{
  display: flex;
  margin-left: 100px;
  width: 100%;
  align-items: center;
  border-bottom: #555;
}
</style>
