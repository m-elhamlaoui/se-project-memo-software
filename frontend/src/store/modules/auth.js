import axios from '../../plugins/axios';
import Toastify from 'toastify-js';
import 'toastify-js/src/toastify.css';
import router from '@/router';


const state = {
  token: localStorage.getItem('token') || '',
  user: JSON.parse(localStorage.getItem('user')) || {}
};

const getters = {
  isAuthenticated: state => !!state.token,
  user: state => state.user
};

const actions = {
  async login({ commit }, credentials) {
    try {
      const response = await axios.post('/auth/login', credentials,{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
      const token = response.data.token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      commit('setToken', token);



      const userresponse = await axios.get("/api/members/email/"+credentials.email);
      localStorage.setItem('user', JSON.stringify(userresponse.data));
      commit('setUser', userresponse.data);
        router.push("/dashboard")
      Toastify({
        text: "Welcome Again !",
        duration: 3000,
        close: true,
        gravity: "bottom", // `top` or `bottom`
        position: "right", // `left`, `center` or `right`
        backgroundColor: "green",
      }).showToast();

    } catch (error) {

        Toastify({
            text: "Wrong Credentials !",
            duration: 3000,
            close: true,
            gravity: "bottom", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            backgroundColor: "red",
          }).showToast();

    }
  },
  async register({ commit }, userData) {
    try {
      const response = await axios.post('/auth/signup', userData,{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
      // Handle registration success (e.g., auto-login or redirect)

      router.push("/login")
      Toastify({
        text: "Successful Sign Up",
        duration: 3000,
        close: true,
        gravity: "bottom", // `top` or `bottom`
        position: "right", // `left`, `center` or `right`
        backgroundColor: "green",
      }).showToast();

    } catch (error) {
        Toastify({
          text: "Handle or Email aleary in use",
          duration: 3000,
          close: true,
          gravity: "bottom", // `top` or `bottom`
          position: "right", // `left`, `center` or `right`
          backgroundColor: "red",
        }).showToast();
    }
  },
  logout({ commit }) {
    console.log("help me ")
    commit('clearAuth');
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    delete axios.defaults.headers.common['Authorization'];
    router.push("/")
    Toastify({
        text: "See you next time !",
        duration: 3000,
        close: true,
        gravity: "bottom", // `top` or `bottom`
        position: "right", // `left`, `center` or `right`
        backgroundColor: "green",
      }).showToast();

  }
};

const mutations = {
  setToken(state, token) {
    state.token = token;
  },
  setUser(state, user) {
    state.user = user;
  },
  clearAuth(state) {
    state.token = '';
    state.user = {};
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};