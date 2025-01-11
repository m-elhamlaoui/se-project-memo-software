import axios from 'axios';
import store from '../store';

const instance = axios.create({
  baseURL: 'http://localhost:8081', // Replace with your backend URL
});

// Add a request interceptor
instance.interceptors.request.use(
  config => {
    const token = store.state.auth.token;
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

instance.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      // Check if this is a login attempt
      if (error.config.url.includes('/login')) {
        // Handle incorrect credentials during login
        Toastify({
          text: "Invalid email or password",
          duration: 3000,
          close: true,
          gravity: "bottom",
          position: "right",
          backgroundColor: "red",
        }).showToast();
      } else {
        // Handle expired token/unauthorized for other requests
        console.error('Token expired or unauthorized. Logging out...');
        await store.dispatch('auth/logout');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default instance;
