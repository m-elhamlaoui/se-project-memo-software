<template>
<div class="min-h-screen bg-gray-100 text-gray-900 flex justify-center">
    <div class="max-w-screen-xl m-0 sm:m-10 bg-white shadow sm:rounded-lg flex justify-center flex-1">
        <div class="lg:w-1/2 xl:w-5/12 p-6 sm:p-12">
            
            <div class="mt-12 flex flex-col items-center">
                <a href="/" class="cursor-pointer text-green-500 underline">
                <HomeIcon class="w-6 h-6 text-green-500 inline mr-2"/>
  <h1 class="inline"> 
 Back to home</h1>
</a>

                <div class="w-full flex-1 mt-8">
                    <div class="flex flex-col items-center">
                        <button @click="this.$router.push('/login')"
                            class="w-full max-w-xs font-bold shadow-sm rounded-lg py-3 bg-green-100 text-gray-800 flex items-center justify-center transition-all duration-300 ease-in-out focus:outline-none hover:shadow focus:shadow-sm focus:shadow-outline">
                            <div class="bg-white p-2 rounded-full">
                               <CubeIcon class="w-6 h-6 text-green-500" />
                            </div>
                            <span class="ml-4">
                                Log in
                            </span>
                        </button>

                    </div>

                    <div class="my-12 border-b text-center">
                        <div
                            class="leading-none px-2 inline-block text-sm text-gray-600 tracking-wide font-medium bg-white transform translate-y-1/2">
                            Or Sign In with Your E-mail
                        </div>
                    </div>

                    <form @submit.prevent="Signup" class="mx-auto max-w-xs">
                        <input
                        v-model="handle"
                            class="w-full px-8 py-4 rounded-lg font-medium bg-gray-100 border border-gray-200 placeholder-gray-500 text-sm focus:outline-none focus:border-gray-400 focus:bg-white mt-5"
                            type="text" placeholder="handle" required/>
    <input
        v-model="email"
        class="w-full px-8 mt-5 py-4 rounded-lg font-medium bg-gray-100 border border-gray-200 placeholder-gray-500 text-sm focus:outline-none focus:border-gray-400 focus:bg-white"
        type="email" 
        placeholder="Email"
        required />
    <input
        v-model="password"
        class="w-full px-8 py-4 rounded-lg font-medium bg-gray-100 border border-gray-200 placeholder-gray-500 text-sm focus:outline-none focus:border-gray-400 focus:bg-white mt-5"
        type="password"
        placeholder="Password"
        required />
    <button
        type="submit"
        class="mt-5 tracking-wide font-semibold bg-green-400 text-white-500 w-full py-4 rounded-lg hover:bg-green-700 transition-all duration-300 ease-in-out flex items-center justify-center focus:shadow-outline focus:outline-none">
        <svg class="w-6 h-6 -ml-2" fill="none" stroke="currentColor" stroke-width="2"
            stroke-linecap="round" stroke-linejoin="round">
            <path d="M16 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2" />
            <circle cx="8.5" cy="7" r="4" />
            <path d="M20 8v6M23 11h-6" />
        </svg>
        <span class="ml-3">Sign in</span>
    </button>
</form>
                </div>
            </div>
        </div>
        <div class="flex-1 bg-green-100 text-center hidden lg:flex">
            <div class="m-12 xl:m-16 w-full bg-contain bg-center bg-no-repeat oop"
                >
            
            </div>
        </div>
    </div>
</div>
</template>

<script>
import { CubeIcon } from '@heroicons/vue/solid';
import { HomeIcon } from '@heroicons/vue/solid';
import { mapActions } from 'vuex';



export default {
    components:{CubeIcon,HomeIcon},
    data(){
        return ({  
            password:"",
            handle:"",
            email:""
        })
    },
    methods:{
        ...mapActions('auth', ['register']),
        Signup(){
            if(this.email.trim()!==""  && this.handle.trim()!=="" && this.password.trim()!==""){
                this.register({"email":this.email.trim(),"password":this.password.trim(),"handle":this.handle.trim()})
            }

        }
    }

}
</script>

<style scoped>
.oop{
    background-image: url('../../assets/background-image-login.webp');
}

</style>