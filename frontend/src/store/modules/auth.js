import axios from '../../plugins/axios';
import Toastify from 'toastify-js';
import 'toastify-js/src/toastify.css';
import router from '@/router';
import WebSocketService from '@/services/WebSocketService'


const state = {
  token: localStorage.getItem('token') || '',
  user: JSON.parse(localStorage.getItem('user')) || {},
  userSubscription:null
};

const getters = {
  isAuthenticated: state => !!state.token,
  user: state => state.user
};

const actions = {
  async login({ commit ,dispatch}, credentials) {
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

      await dispatch('setupUserSocket');
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
            text: "Wrong Crendentials !",
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
    commit('clearAuth');
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    WebSocketService.disconnect();
    commit('setUserSubscription', null);
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
  },
  async setupUserSocket({ commit, state }) {
    // Disconnect any existing subscription
    if (state.userSubscription) {
      state.userSubscription.unsubscribe();
    }
    // Setup new user update subscription
    const subscription = WebSocketService.subscribe(
      `/update/user/${state.user.email}`, 
      (updatedUser) => {
        console.log("update received");
        
        // Handle user update
        commit('setUser', updatedUser);
        localStorage.setItem('user', JSON.stringify(updatedUser));
      }
    );
    commit('setUserSubscription', subscription);
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
  },
  setUserSubscription(state, subscription) {
    state.userSubscription = subscription;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};