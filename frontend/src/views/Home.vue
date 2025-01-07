<template>
<Home/>
<AboutUs/>
<Wallet/>
<VotingSystem/>
  
</template>

<script>
import Home from '../components/home/Home.vue' ; 
import AboutUs from '../components/home/AboutUs.vue';
import Wallet from '../components/home/Wallet.vue';
import VotingSystem from '../components/home/VotingSystem.vue';
export default {
    components:{
        Home,AboutUs,Wallet,VotingSystem
    }

}
</script>

<style>

</style>