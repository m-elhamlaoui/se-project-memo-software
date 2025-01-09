import axios from '../../plugins/axios';
import Toastify from 'toastify-js';
import 'toastify-js/src/toastify.css';
import router from '@/router';

const actions = {
  async create({commit},data){
    try {
      const response = await axios.post('/api/taskblocks/create', data,{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
        Toastify({
            text: "TaskBlock Created",
            duration: 3000,
            close: true,
            gravity: "bottom", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            backgroundColor: "green",
          }).showToast();

    } catch (error) {

        Toastify({
            text: "Something Went Wrong",
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



export default {
  namespaced: true,
  actions,

};