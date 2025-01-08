<template>
  <div class="discussion-wrapper">
    <!-- Return Link -->
    <div class="return-link">
      <a href="/" class="text-green-500 hover:text-green-700 font-medium">
        &larr; Back to Dashboard
      </a>
    </div>

    <!-- Discussion History -->
    <div class="discussion-history">

        <!-- start righting here -->
    <div class="input-bar">
    <!-- TaskBlock Title Input -->
    <h1>TaskBlock Title</h1>
    <input v-model="taskTitle" placeholder="TaskBlock Title" class="input-field" />

    
    <!-- TaskBlock Member -->
     <b-form-group label="Member">
      <div class="input-group mb-3">
        <b-form-tags
          label="Assigned to"
          input-id="tags-remove-on-delete"
          :input-attrs="{ 'aria-describedby': 'tags-remove-on-delete-help' }"
          v-model="selectedAssignees"
          :tag-validator="ValidateMember"
          separator=" "
          placeholder="Enter new tags separated by space"
          remove-on-delete
          no-add-on-enter
          class="flex-grow-1"
        ></b-form-tags>
        <b-dropdown
          id="dropdown-1"
          text="Select a Member"
          class="ml-2"
          style="background-color:orange;border-color:orange"

          v-if="availableMembers.length"
        >
          <b-dropdown-item
            v-for="(option, index) in availableMembers"
            :key="index"
            @click="addAssignedto(option)"
          >
            {{ option }}
          </b-dropdown-item>
        </b-dropdown>
      </div>
    </b-form-group>



    <!-- Create New Subgroup -->
    <b-form-group label="Create a New Subgroup" class="mb-4">
      <div class="flex items-center">
        <b-form-input
          v-model="newSubgroupName"
          placeholder="Enter subgroup name"
          class="flex-grow mr-2"
        ></b-form-input>
        <b-button
          @click="createNewSubgroup"
          variant="success"
          style="background-color: green; border-color: green;"
        >
          Create Subgroup
        </b-button>
      </div>
    </b-form-group>

    <!-- Dynamic Subgroups -->
    <div v-for="(subgroup, index) in subgroups" :key="index" class="mb-4">
      <b-form-group :label="'Subgroup: @' + subgroup.name">
       
        <div class="input-group">
          <!-- Tag Input -->
          <b-form-tags
            :label="'Members of ' + subgroup.name"
            :input-id="'tags-' + index"
            :input-attrs="{ 'aria-describedby': 'tags-help-' + index }"
            v-model="subgroup.members"
            :tag-validator="(tag)=> ValidateMemberSubgroup(tag,index)"
            separator=" "
            placeholder="Add members separated by space"
            remove-on-delete
            no-add-on-enter
            class="flex-grow-1"
          ></b-form-tags>

          <!-- Dropdown for Available Members -->
          <b-dropdown
            :id="'dropdown-' + index"
            text="Select a Member"
            class="ml-2"
            style="background-color: orange; border-color: orange;"
            v-if="availableMembers.length"
          >
            <b-dropdown-item
              v-for="(option, i) in availableMembers"
              :key="i"
              @click="addMember(subgroup, option)"
            >
              {{ option }}
              
            </b-dropdown-item>
          </b-dropdown>



           <b-button
          @click="deleteSubgroup(index)"
          variant="danger"
          size="sm"
        >
          Delete
        </b-button>




        </div>
      </b-form-group>
    </div>


    <div class="mb-4">
      <label for="number-input" class="font-semibold block mb-2">Enter Acceptance Pourcentage Policy(0 to 100):</label>
      <input
        id="number-input"
        type="number"
        v-model="numberValue"
        @input="validateNumberInput"
        placeholder="Enter a number"
        class="border rounded-md p-2 w-full"
      />
      <p v-if="numberError" class="text-red-500 text-sm mt-1">{{ numberError }}</p>
    </div>

    <!-- Time Input -->
    <div class="mb-4">
      <label class="font-semibold block mb-2">Enter Voting Period Policy</label>
      <div class="flex space-x-2">
        <input
          type="number"
          v-model="days"
          placeholder="Days"
          min="0"
          max="1000"
          class="border rounded-md p-2 w-20"
        />
        <input
          type="number"
          v-model="hours"
          placeholder="Hours"
          min="0"
          max="23"
          class="border rounded-md p-2 w-20"
        />
        <input
          type="number"
          v-model="minutes"
          placeholder="Minutes"
          min="0"
          max="59"
          class="border rounded-md p-2 w-20"
        />
        <input
          type="number"
          v-model="seconds"
          placeholder="Seconds"
          min="0"
          max="59"
          class="border rounded-md p-2 w-20"
        />
      </div>
      <p v-if="timeError" class="text-red-500 text-sm mt-1">{{ timeError }}</p>
    </div>
    

    <!-- Send Task Button -->
    <button @click="sendTask" class="send-btn bg-green-400">Create  Task</button>
  </div>
    

  </div>

    
  </div>
</template>


<script>
export default {
  data() {
    return {
    days:null,
    hours:null,
    minutes:null,
    seconds:null,
    newSubgroupName:"",
    showtaskadd:false,
      newTask: '',
      showstats:true,
    subgroups: [],
      filteredMembers:[],
      taskTitle: '',
      taskDescription: '',
      taskDeadline: '',
      selectedAssignees: [],
      selectedSubgroup: [],
      availableMembers: ['Alice', 'Bob', 'Charlie'], // Example member list
      availableSubgroups: ['Frontend Team', 'Backend Team', 'Design Team'] // Example subgroup list
    };
  },
  methods: {
    createNewSubgroup() {
      // Validate and add a new subgroup
      if (this.newSubgroupName.trim() !== "") {

        const nameExists = this.subgroups.some(
        (subgroup) => subgroup.name === this.newSubgroupName.trim()
      );

      if (nameExists) {
        alert("A subgroup with this name already exists.");
        return;
      }
        
        this.subgroups.push({
          name: this.newSubgroupName,
          members: [],
        });
        this.newSubgroupName = ""; // Reset input
      } else {
        alert("Subgroup name cannot be empty.");
      }
    },
    addMember(subgroup, member) {

      if (!subgroup.members.includes(member)) {
        subgroup.members.push(member);
      }


    },
     deleteSubgroup(index) {
      // Remove the subgroup at the specified index
      if (confirm("Are you sure you want to delete this subgroup?")) {
        this.subgroups.splice(index, 1);
      }
    },
    addTaskBlock() {
    },
     handleTagState(event) {

      const searchtag = event.target.value;
     
      if (!searchtag) {
        this.filteredMembers = []
      }
      else{
         this.filteredMembers = this.availableMembers.filter((user) => {
          const username = user.toLowerCase();
          return username.includes(searchtag.toLowerCase()) && !this.selectedAssignees.includes(user)  ;  
      });

      }

    
      
    }
    ,
    ValidateMember(tag){
      
      return (this.availableMembers.includes(tag) && !this.selectedAssignees.includes(tag))

    }, 
    ValidateMemberSubgroup(tag,index){
      
      return (this.availableMembers.includes(tag) && !this.subgroups[index].members.includes(tag))

    }, 
    ValidateSubgroup(tag){
      
      return (this.availableSubgroups.includes(tag) && !this.availableSubgroups.includes(tag))

    }, 
     addAssignedto(tag) {
      if (!this.selectedAssignees.includes(tag)) {
        this.selectedAssignees.push(tag);
      }
    },
    addSubgroup(tag){
         if (!this.selectedSubgroup.includes(tag)) {
        this.selectedSubgroup.push(tag);
      }

    }
  },
};
</script>

<style scoped>
.discussion-wrapper {
  display: flex;
  width:100%;
  flex-direction: column;
  height: 100vh;
  background-color: rgba(229, 231, 235, 0.8);
}

.return-link {
  padding: 16px;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  margin: 16px;
}

.discussion-history {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  margin: 16px;
}

.message-card {
  margin-bottom: 16px;
  padding: 12px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  background-color: white;
  transition: transform 0.3s ease-in-out, background-color 0.3s;
}

.vote-status-tag {
  transform: translateY(-50%);
}


.task-card {
  cursor: pointer;
}

.task-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}

.progress-bar {
  margin-top: 8px;
}

.bar-container {
  background-color: #e5e7eb;
  height: 8px;
  border-radius: 4px;
  overflow: hidden;
}

.bar {
  height: 8px;
  transition: width 0.3s;
}

.accepted-bar {
  background-color: #4caf50;
}

.rejected-bar {
  background-color: #f44336;
}

.chain-animation .chain-icon {
  width: 32px;
  height: 32px;
  stroke: #4caf50;
}

.input-bar {
  display: flex;
  gap: 12px;
  padding: 16px;
  background-color: white;
  border-top: 1px solid #e5e7eb;
}

.input-field {
  flex: 1;
  padding: 8px 12px;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
  font-size: 16px;
}

.send-btn {
  background-color: rgb(36, 162, 36);
  color: white;
  padding: 8px 16px;
  border-radius: 6px;
  font-size: 16px;
  transition: background-color 0.3s;
}

.send-btn:hover {
  background-color: rgb(2, 102, 2);
}

.input-bar {
  background: rgba(255, 255, 255, 0.9);
  padding: 16px;
  border-radius: 8px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 12px;
}

/* Task Input */
.input-field {
  padding: 8px 12px;
  border-radius: 4px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  font-size: 14px;
  margin-bottom: 8px;
}

/* Dropdown for Assigning Members */
.dropdown {
  display: flex;
  align-items: center;
  gap: 8px;
}
.dropdown-select {
  padding: 8px 12px;
  border-radius: 4px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  font-size: 14px;
  min-width: 150px;
}
.dropdown-add-btn {
  background: rgba(0, 100, 200, 0.8);
  color: white;
  padding: 8px 12px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  transition: background 0.3s;
}
.dropdown-add-btn:hover {
  background: rgba(0, 100, 200, 1);
}

/* Subgroups Tags Input */
.subgroups-input {
  display: flex;
  flex-direction: column;
}
.subgroup-input-field {
  border: 1px solid rgba(0, 0, 0, 0.2);
  padding: 8px;
  border-radius: 4px;
  font-size: 14px;
  margin-bottom: 8px;
  width: 100%;
}
.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 8px;
}
.tag {
  background: rgba(0, 0, 0, 0.1);
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 14px;
  display: flex;
  align-items: center;
}
.tag-remove-btn {
  margin-left: 8px;
  background: none;
  border: none;
  color: rgba(200, 0, 0, 0.8);
  cursor: pointer;
  font-size: 16px;
}


</style>
