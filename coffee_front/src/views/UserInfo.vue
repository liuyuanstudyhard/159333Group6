<template>
  <div class="user-info-container">
    <el-card class="user-info-card">
      <div class="header">
        <!-- Back button icon -->
        <el-button icon="el-icon-back" @click="goHome" class="back-button" />
        <!-- User Information Title -->
        <h2>User Information</h2>
      </div>

      <el-form :model="userInfo" :rules="userInfoRules" ref="userInfoForm">
        <el-form-item label="Username" prop="username">
          <el-input v-model="userInfo.username" placeholder="Username" disabled />
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="userInfo.name" placeholder="Name" />
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="userInfo.email" placeholder="Email" />
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="userInfo.phone" placeholder="Phone" />
        </el-form-item>
        <el-form-item label="Gender" prop="gender">
          <el-input v-model="userInfo.gender" placeholder="Gender" />
        </el-form-item>
        <el-form-item label="Address" prop="address">
          <el-input v-model="userInfo.address" placeholder="Address" />
        </el-form-item>
        <el-button type="primary" @click="updateUserInfo" class="save-button">Save Changes</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userInfo: {
        username: '',
        name: '',
        email: '',
        phone: '',
        gender: '',
        address: ''
      },
      userInfoRules: {
        name: [
          { required: true, message: 'Please enter your name', trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'Please enter your email', trigger: 'blur' },
          { type: 'email', message: 'Please enter a valid email address', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: 'Please enter your phone number', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: 'Please enter your gender', trigger: 'blur' }
        ],
        address: [
          { required: true, message: 'Please enter your address', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    // Fetch user information
    getUserInfo() {
      const userId = localStorage.getItem('userId');  // Get userId from localStorage
      axios.get(`${this.$httpUrl}/user/${userId}`)
          .then(response => {
            this.userInfo = response.data; // Populate the form with user info
            console.log('User information loaded:', this.userInfo);
          })
          .catch(error => {
            console.error('Failed to load user information:', error.response.data.error);
            this.$message.error('Failed to load user information: ' + error.response.data.error);
          });
    },

    // Update user information
    updateUserInfo() {
      const userId = localStorage.getItem('userId');
      this.$refs.userInfoForm.validate((valid) => {
        if (valid) {
          axios.put(`${this.$httpUrl}/user/${userId}`, this.userInfo)
              .then(response => {
                console.log('User information updated:', response.data);
                this.$message.success('User information updated successfully!');
              })
              .catch(error => {
                console.error('Failed to update user information:', error.response.data.error);
                this.$message.error('Failed to update user information: ' + error.response.data.error);
              });
        }
      });
    },

    // Go back to home page
    goHome() {
      this.$router.push('/');  // Redirect to home route
    }
  },
  created() {
    this.getUserInfo(); // Fetch user information when the page loads
  }
};
</script>

<style scoped>
.user-info-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.user-info-card {
  width: 500px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
  position: relative;
}

.header h2 {
  margin: 0;
  font-size: 20px;
  flex-grow: 1;
  text-align: center;
}

.back-button {
  border: none;
  background-color: transparent;
  color: #606266;
  padding: 0;
  font-size: 24px;
  margin-right: auto;
}

.save-button {
  width: 100%;
  margin-top: 20px;
  background-color: #42b983;
  color: white;
  font-weight: bold;
  border-radius: 5px;
}
</style>
