import { createStore } from 'vuex';
import auth from './modules/auth';
import taskblocks from './modules/taskblocks';
import tasks from './modules/tasks';
import votes from './modules/votes';
import member from './modules/member';


// in this part you will define the functionality 
// to connect to the backend

export default createStore({
  modules: {
    auth,
    taskblocks,
    tasks,
    votes,
    member
  }
});
