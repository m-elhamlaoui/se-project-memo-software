import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import './styles/main.css';
import AOS from 'aos';
import 'aos/dist/aos.css';

const app = createApp(App);

app.use(store);
app.use(router);
AOS.init({
    duration: 1000,
  });

app.mount('#app');