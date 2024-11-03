<template>
  <div class="auth-container">
    <el-card class="auth-card">
      <el-tabs v-model="activeTab">
        <el-tab-pane label="Login" name="login">
          <el-form :model="loginForm" :rules="loginRules" ref="loginForm">
            <el-form-item label="Username" prop="username">
              <el-input v-model="loginForm.username" placeholder="Enter your username" />
            </el-form-item>
            <el-form-item label="Password" prop="password">
              <el-input v-model="loginForm.password" placeholder="Enter your password" show-password />
            </el-form-item>
            <el-button type="primary" @click="login" class="auth-button">Login</el-button>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="Register" name="register">
          <el-form :model="registerForm" :rules="registerRules" ref="registerForm">
            <el-form-item label="Username" prop="username">
              <el-input v-model="registerForm.username" placeholder="Enter your username" />
            </el-form-item>
            <el-form-item label="Password" prop="password">
              <el-input v-model="registerForm.password" placeholder="Enter your password" show-password />
            </el-form-item>
            <el-form-item label="Name" prop="name">
              <el-input v-model="registerForm.name" placeholder="Enter your name" />
            </el-form-item>
            <el-form-item label="Email" prop="email">
              <el-input v-model="registerForm.email" placeholder="Enter your email" />
            </el-form-item>
            <el-form-item label="Phone" prop="phone">
              <el-input v-model="registerForm.phone" placeholder="Enter your phone number" />
            </el-form-item>
            <el-form-item label="Gender" prop="gender">
              <el-input v-model="registerForm.gender" placeholder="Enter your gender" />
            </el-form-item>
            <el-form-item label="Address" prop="address">
              <el-input v-model="registerForm.address" placeholder="Enter your address" />
            </el-form-item>
            <el-button type="primary" @click="register" class="auth-button">Register</el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios';


export default {
  data() {
    return {
      activeTab: 'login',

      loginForm: {
        username: '',
        password: '',
      },
      loginRules: {
        username: [
          { required: true, message: 'Please enter your username', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' }
        ]
      },

      registerForm: {
        username: '',
        password: '',
        name: '',
        email: '',
        phone: '',
        gender: '',
        address: ''
      },
      registerRules: {
        username: [
          { required: true, message: 'Please enter your username', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' }
        ],
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
    resetRegisterForm() {
      this.registerForm = {
        username: '',
        password: '',
        name: '',
        email: '',
        phone: '',
        gender: '',
        address: ''
      };
      this.$refs.registerForm.resetFields();
    },
    login() {
      this.$refs.loginForm.validate((valid) => {

        if (valid) {




          axios.post(`${this.$httpUrl}/user/login`, {
            username: this.loginForm.username,
            password: this.loginForm.password
          })
              .then(response => {
                const { userId } = response.data;
                localStorage.setItem('userId', userId);

                if (userId === 999){
                  this.$message.success('Admin Login successful!');
                }else {
                  this.$message.success('Login successful!');
                }
                this.$store.commit('setUserId', userId);

                const redirectPath = this.$store.state.redirectPath || '/';
                this.$store.commit('clearRedirectPath');
                this.$router.push(redirectPath);
              })
              .catch(error => {
                this.$message.error('Login failed: ' + error.response.data.error);
              });
          }

      });
    },

    register() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          axios.post(`${this.$httpUrl}/user/register`, {
            username: this.registerForm.username,
            password: this.registerForm.password,
            name: this.registerForm.name,
            email: this.registerForm.email,
            phone: this.registerForm.phone,
            gender: this.registerForm.gender,
            address: this.registerForm.address
          })
              .then(response => {
                const { userId } = response.data;
                if (userId == -1) {
                  this.$message.warning('Account already exists, please do not register again!');
                } else {
                  this.$message.success('Registration successful!');
                  this.resetRegisterForm();
                  this.activeTab = 'login';
                }
              })
              .catch(error => {
                this.$message.error('Registration failed: ' + error.response.data.error);
              });
        }
      });
    }
  }
};
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.auth-card {
  width: 400px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.auth-button {
  width: 100%;
  margin-top: 20px;
  background-color: #42b983;
  color: white;
  font-weight: bold;
  border-radius: 5px;
}
</style>
