import axios from '../../plugins/axios';
import Toastify from 'toastify-js';
import 'toastify-js/src/toastify.css';
import router from '@/router';

const state = {
    wallets:null,
    taskblock:null
  };

  const getters = {
    wallets: state => state.wallets
  };

const actions = {
  async createTask({commit},data){
    try {
        console.log("here here here here ehr ehr ehe rher erhe ",data,data.id);
        
        const ii = data.id
        const qq = data
        delete qq.id    
      const response = await axios.post('/api/tasks/'+ii, qq,{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
        Toastify({
            text: "Task Added",
            duration: 3000,
            close: true,
            gravity: "bottom", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            backgroundColor: "green",
          }).showToast();

    } catch (error) {

        Toastify({
            text: "Task Added",
            duration: 3000,
            close: true,
            gravity: "bottom", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            backgroundColor: "green",
          }).showToast();
          return []

    }
},
    async getgroups({commit},data){
        try {
            const response = await axios.get('/api/taskblocks/' + data + '/groups', {},{
              headers: {
                'Content-Type': 'application/json', // Explicitly setting the content type to JSON
              }});
      
              return response.data
            
          } catch (error) {
      
              Toastify({
                  text: "Something Went Wrong, Try reloading the Page",
                  duration: 3000,
                  close: true,
                  gravity: "bottom", // `top` or `bottom`
                  position: "right", // `left`, `center` or `right`
                  backgroundColor: "orange",
                }).showToast();
              return []
      
          }
    }

};

const mutations = {
    setwallets(state, wallets) {
      state.wallets = wallets;
    },
    settaskblock(state,taskblock){
        state.taskblock = taskblock
    }
  };


export default {
  namespaced: true,
  actions,
  state,
  getters,
  mutations

};