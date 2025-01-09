<template>
  <div class="relative flex  flex-col justify-center overflow-hidden py-6 sm:py-12">
    <div
        class="group relative cursor-pointer overflow-hidden bg-white px-6 pt-10 pb-8 shadow-xl ring-1 ring-gray-900/5 transition-all duration-300 hover:-translate-y-1 hover:shadow-2xl sm:mx-auto sm:max-w-sm sm:rounded-lg sm:px-10">
        <span class="absolute top-10 z-0 h-20 w-20 rounded-full bg-green-500 transition-all duration-300 group-hover:scale-[10]"></span>
        <div class="relative z-10 mx-auto max-w-md">
            <span class="grid h-20 w-20 place-items-center rounded-full bg-green-500 transition-all duration-300 group-hover:bg-green-400">
                <CubeIcon class="text-white w-6 h-6"/>
            </span>
            <div
                class="space-y-6 pt-5 text-base leading-7 text-gray-600 transition-all duration-300 group-hover:text-white/90">
                <p class="text-container">title</p>
            </div>
            <div class="pt-5 text-base font-semibold leading-7">
                <p>
                    <a href="#" class="text-green-500 transition-all duration-300 group-hover:text-white">  + Add a Task
                        &rarr;
                    </a>
                </p>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import { CubeIcon } from '@heroicons/vue/solid'

export default {
    components :{CubeIcon},
    props:['title']
}
</script>

<style scoped>

.text-container {
  display: -webkit-box; /* Necessary for limiting lines */
  -webkit-line-clamp: 3; /* Number of lines to display */
  -webkit-box-orient: vertical; /* Required for the ellipsis effect */
  overflow: hidden; /* Hides the extra text */
  text-overflow: ellipsis; /* Adds the ellipsis (...) at the end */
  white-space: normal; /* Prevents wrapping when combined with -webkit-box */
  font-size: 16px;
  line-height: 1.5; /* Adjust line height as needed */
}

</style>