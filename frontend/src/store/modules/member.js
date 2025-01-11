import axios from '../../plugins/axios';
import Toastify from 'toastify-js';
import 'toastify-js/src/toastify.css';
import router from '@/router';

const actions = {
  async getUsers({ commit }, credentials) {
    try {
      const response = await axios.get('/api/members', {},{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});


        return response.data

    } catch (error) {

        Toastify({
            text: "Something went Wrong !",
            duration: 3000,
            close: true,
            gravity: "bottom", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            backgroundColor: "red",
          }).showToast();
          return []

    }
  },

  async invite({ commit }, data) {
    try {
      const response = await axios.post('/api/members/invite?inviterId='+data.id1+'&inviteeId='+data.id2, {},{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
        Toastify({
            text: "Invitation Sent successfully",
            duration: 3000,
            close: true,
            gravity: "bottom", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            backgroundColor: "green",
          }).showToast();


        return response.data

    } catch (error) {

        Toastify({
            text: "Invitation already sent !",
            duration: 3000,
            close: true,
            gravity: "bottom", // `top` or `bottom`
            position: "right", // `left`, `center` or `right`
            backgroundColor: "orange",
          }).showToast();
          return []

    }
  },
  async accept({commit},data){
    try {
      const response = await axios.post('/api/friends/invite/'+data.id, {},{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
        Toastify({
            text: "Invitation accepted",
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

  },
  async reject({commit},data){
    try {
      const response = await axios.post('/api/friends/reject/'+data.id, {},{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
        Toastify({
            text: "Invitation rejected",
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

  },
  async remove({commit},data){
    try {
      const response = await axios.post('/api/friends/remove/'+data.id, {},{
        headers: {
          'Content-Type': 'application/json', // Explicitly setting the content type to JSON
        }});
        Toastify({
            text: "Friend Removed",
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