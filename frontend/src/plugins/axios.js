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
    (response) => response, // Pass through successful responses
    async (error) => {
      if (error.response?.status === 401) {
        console.error('Token expired or unauthorized. Logging out...');
        // Dispatch the Vuex logout action
        store.dispatch('auth/logout');
  
        // Optionally redirect the user to the login page
        window.location.href = '/login'; // Replace with your login route
      }
      return Promise.reject(error); // Pass the error to the caller
    }
  );

export default instance;
