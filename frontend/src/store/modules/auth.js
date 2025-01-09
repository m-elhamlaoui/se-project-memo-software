import axios from '../../plugins/axios';

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


      
      const userresponse = await axios.get("/api/users/email/"+credentials.email);
      localStorage.setItem('user', JSON.stringify(userresponse.data));
      commit('setUser', userresponse.data);
      console.log(userresponse.data)
      return 1;
    } catch (error) {
      return 0;
    }
  },
  async register({ commit }, userData) {
    try {
      const response = await axios.post('/auth/signup', userData,{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
      // Handle registration success (e.g., auto-login or redirect)
      return true;
    } catch (error) {
      return "the handle or the email is already used";
    }
  },
  logout({ commit }) {
    commit('clearAuth');
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    delete axios.defaults.headers.common['Authorization'];
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