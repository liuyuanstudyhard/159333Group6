<template>
    <div class="header" :style="{ margin: '0 auto', position: 'fixed', top: 0, left: 0, right: 0, zIndex: 1000 }">
        <div class="header-content">
            <img src="@/assets/coffee.png" alt="Logo" class="logo" />
            <div class="user-actions">
               
                <template v-if="isLoggedIn">

                    <el-button class="custom-button" icon="el-icon-user" @click="goToUserInfo">User Info</el-button>

                    <el-button class="custom-button" icon="el-icon-tickets" @click="goToOrderManagement">Order Management</el-button>


                  <el-button class="custom-button" icon="el-icon-switch-button" @click="logout">Logout</el-button>
                </template>
                <template v-else>

                    <el-button class="custom-button" icon="el-icon-user" @click="showLoginDialog">Login</el-button>
                </template>

                <template v-if="isLoggedIn && !isAdmin">
                  <el-badge :value="cartItemCount" class="cart-badge">
                      <el-button class="custom-button" icon="el-icon-shopping-cart-1" @click="showCartDialog">Cart</el-button>
                  </el-badge>
                </template>
            </div>
        </div>

        <div class="info-container">
            <el-divider type="dashed" />
            <div class="info">
                <span class="s"><i class="el-icon-time"></i>{{ selectedDay }} @ {{selectedTime}}</span>
                <span class="s"><i class="el-icon-position"></i>PICK-UP FROM: {{ address }}</span>
                <el-button class="s" style="font-weight: bold" @click="showChangeDialog">CHANGE</el-button>
            </div>
        </div>


        <el-dialog title="Login" :visible.sync="loginDialogVisible" :modal="false">
            <p>Login Form Goes Here</p>
        </el-dialog>

        <el-dialog
            :visible.sync="cartDialogVisible"
            :close-on-click-modal="false"
            :before-close="handleClose"
            title="Pick-Up from"
            width="600px"
            :modal="false"
        >
            <div class="pickup-info">
                <h2 style="text-align: center">{{ address }}</h2>
                <p style="text-align: center;color: #AC9DBE">{{ detailedAddress }}</p>
                <br>
                <span class="s" style="padding-left: 50px"><i class="el-icon-time"></i>{{ selectedDay }} @ {{selectedTime}}</span>
                <span class="s"><i class="el-icon-position"></i>PICK-UP FROM: {{ address }}</span>
            </div>
            <div class="orders-list" v-if="orders.length">
                <el-scrollbar style=";">
                    <div v-for="(order, index) in orders" :key="index" class="order-item">
                        <div class="order-details">
                            <el-image :src="order.image" class="order-image" fit="contain" />
                            <div>
                                <h4>{{ order.name }} </h4>
                                <p style="color: gray ; margin-left: 10px;font-size: 12px">{{ order.size }} · ${{ order.newPrice }}</p>
                                <el-input-number v-model="order.selfQuantity" :min="0" @change="updateTotal($event,index,order)" size="mini"></el-input-number>

                            </div>
                        </div>
                        <div class="order-quantity">
                            <el-button type="danger" @click="removeOrder(index)">Remove</el-button>
                        </div>
                    </div>
                </el-scrollbar>
            </div>
            <div class="checkout-summary">
                <h4>Total Items: {{ cartItemCount }}</h4>
                <h4>Total Price: ${{ totalPrice.toFixed(2) }}</h4>
                <el-button type="success" @click="showPayDialog" size="large">Checkout • ${{ totalPrice.toFixed(2) }}</el-button>
            </div>
        </el-dialog>



        <el-dialog title="Change Details" :visible.sync="changeDialogVisible" :modal="false" class="change-dialog">
            <div class="dialog-content">
                <el-input v-model="address" placeholder="Enter Address" class="dialog-input"></el-input>
                <el-input v-model="detailedAddress" placeholder="Enter Detailed Address" class="dialog-input"></el-input>

                <div class="select-container">
                    <div class="select-item">
                        <span>Select Day:</span>
                        <el-select v-model="selectedDay" placeholder="Select Day" class="dialog-select">
                            <el-option v-for="day in days" :key="day" :label="day" :value="day"></el-option>
                        </el-select>
                    </div>

                    <div class="select-item">
                        <span>Select Time:</span>
                        <el-select v-model="selectedTime" placeholder="Select Time" class="dialog-select">
                            <el-option v-for="time in times" :key="time" :label="time" :value="time"></el-option>
                        </el-select>
                    </div>
                </div>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="changeDialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="updateOrder">Update</el-button>
            </span>
        </el-dialog>


      <el-dialog title="Pay" :visible.sync="paymentDialogVisible" :modal="false" width="600px">
        <div class="dialog-content" style="margin: 0 auto">

          <img src="../assets/QRcode.png">
        </div>

        <span slot="footer" class="dialog-footer">
                <el-button @click="paymentDialogVisible = false">Cancel</el-button>
                <el-button type="success" @click="checkout">Have paid
      </el-button>
            </span>
      </el-dialog>
    </div>
</template>

<script>

import axios from "axios";

export default {
    name: 'HeaderComponent',
    props: {
        callFeaturedMethod: {
            type: Function,
            required: true
        }
    },
    data() {
        return {
            loginDialogVisible: false,
            cartDialogVisible: false,
            changeDialogVisible: false,
            paymentDialogVisible: false,
            address: '',
            detailedAddress: '',
            selectedDay: '',
            selectedTime: '',
            days: this.generateDays(),
            times: this.generateTimes(),
        };
    },
    computed: {
        cartItemCount(){
            return this.$store.getters.getAllItemsQuantity;
        },
        orders() {
            return this.$store.getters.getAllOrders;
        },
        totalPrice() {
            return this.$store.getters.getTotalPrice;
        },
        isLoggedIn() {
            console.log("User ID from Vuex:", this.$store.getters.getUserId);
            const isLogin = this.$store.getters.getUserId != -1;
            console.log("isLogin:", isLogin);
            return isLogin;
        },
      isAdmin(){
       const userId = localStorage.getItem('userId');
       return userId === "999";
      }
    },
    methods: {
        showLoginDialog() {
            this.loginDialogVisible = true;
            this.$router.push({ name: 'login' });

        },
        logout() {
            this.$store.commit('setUserId', -1);
            localStorage.setItem('userId',-1);
            const redirectPath = this.$store.state.redirectPath || '/';
                    this.$store.commit('clearRedirectPath');
                    this.$router.push(redirectPath);
                    window.location.reload()
        },
      goToOrderManagement() {
        this.$router.push({ name: 'orderManagement' });
      },
        showCartDialog() {
            this.cartDialogVisible = true;
        },
        showChangeDialog() {
            this.changeDialogVisible = true;
        },
        goToUserInfo() {
            this.$router.push({ name: 'userInfo' });
        },
        generateDays() {
            const days = [];
            const today = new Date();
            for (let i = 0; i < 7; i++) {
                const date = new Date(today);
                date.setDate(today.getDate() + i);
                days.push(date.toLocaleDateString());
            }
            return days;
        },
        generateTimes() {
            const times = [];
            for (let hour = 0; hour < 24; hour++) {
                for (let minute = 0; minute < 60; minute += 30) {
                    const ampm = hour >= 12 ? 'PM' : 'AM';
                    const displayHour = hour % 12 === 0 ? 12 : hour % 12;
                    const displayMinute = minute === 0 ? '00' : minute;
                    times.push(`${displayHour}:${displayMinute} ${ampm}`);
                }
            }
            return times;
        },
        updateOrder() {
            this.changeDialogVisible = false;
            this.$store.commit('setAddress', {
                address: this.address,
                detailedAddress: this.detailedAddress,
                selectedDay: this.selectedDay,
                selectedTime: this.selectedTime,
            });
        },
        handleClose(done) {
            done();
        },
        updateTotal(newValue,index,order) {
            let quantityCount = 0 ;
            this.orders.forEach((o) => {
                if (o.id === order.id) {
                    quantityCount += o.selfQuantity;
                }
            })

            this.$store.commit('setItemQuantity', {
                id: order.id,
                quantity: quantityCount,
            });

            this.$store.commit('updateOrder', {
                index: index,
                order: order,
            });
            this.callFeaturedMethod();

        },
        removeOrder(index) {
            let order = this.orders[index];
            const oldQuantity = this.$store.getters.getItemQuantity(order.id);
            this.$store.commit('setItemQuantity', {
                id: order.id,
                quantity: oldQuantity - order.selfQuantity,
            });
            this.$store.commit('removeOrder', index);
            this.callFeaturedMethod();
        },
        showPayDialog(){
          this.paymentDialogVisible = true;
        },
        checkout() {

            this.cartDialogVisible = false;
            this.paymentDialogVisible = false;

          const userId = this.$store.getters.getUserId;

          axios.get(`${this.$httpUrl}/user/${userId}`)
              .then(response => {
                this.userInfo = response.data;
                this.$store.state.address = this.userInfo.address
                console.log('User information loaded:', this.userInfo);

              })
              .catch(error => {
                console.error('Failed to load user information:', error.response.data.error);
                this.$message.error('Failed to load user information: ' + error.response.data.error);
              });


          const now = new Date();

          const hours = String(now.getHours()).padStart(2, '0');
          const minutes = String(now.getMinutes()).padStart(2, '0');
          const seconds = String(now.getSeconds()).padStart(2, '0');
          const orderData = {
            date:now.toLocaleDateString(),
            time: `${hours}:${minutes}:${seconds}`,
            address: this.$store.getters.getAddress,
            price: this.totalPrice.toFixed(2),
            userId: userId,
            orderStatus: 'Pending',
            productDetails: this.orders.map(order => {
              return `${order.name} - $${order.newPrice} (x${order.selfQuantity})`;
            }).join(', ')

          };

          axios.post(`${this.$httpUrl}/order/add`, orderData)
              .then(() => {
                alert("Order placed successfully!");
                this.$store.commit('setAllItemsQuantityToZero');
                this.$store.commit('clearAllOrders');
                this.cartDialogVisible = false;
                this.callFeaturedMethod();
              })
              .catch(error => {
                console.error("Error placing order:", error);
                this.$message.error("There was an error placing your order. Please try again.");
              });
        },
    },
    created() {
        const addressData = this.$store.getters.getAddress;
        this.address = addressData.address;
        this.detailedAddress = addressData.detailedAddress;
        this.selectedDay = addressData.selectedDay;
        this.selectedTime = addressData.selectedTime;
    }
}
</script>

<style scoped>
.order-image {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 20px;
    margin-right: 10px;
}
.header {
    background-color: white;
    padding: 10px 20px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.header-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 1200px;
    margin: 0 auto;
    height: 80px;
}

.user-actions {
    display: flex;
    align-items: center;
}

.info {
    max-width: 1200px;
    margin: 10px auto 0;
    text-align: center;
    font-weight: bold;
}
.s {
   margin: 0 30px;
}
.logo {
    height: 75px;
    width: 50px;
    margin-right: 10px;
}
.custom-button {
    background-color: #42b983;
    color: white;
    border-radius: 5px;
    padding: 10px 20px;
    margin-right: 10px;
    font-weight: bold;
    font-size: 18px;
}

.dialog-content {
    padding: 20px;
}

.dialog-input {
    margin-bottom: 15px;
}

.select-container {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.select-item {
    display: flex;
    align-items: center;
}

.select-item span {
    margin-right: 10px;
}

.dialog-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.el-button + .el-button {
    margin-left: 10px;
}
.pickup-info {
    margin-bottom: 20px;
    background-color: #EFEFEF;
    padding: 20px;
}
.orders-list {
    margin-bottom: 20px;
}
.order-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #f0f0f0;
    padding: 10px 0;
}
.checkout-summary {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.order-details {
    display: flex;
    align-items: center;
}
</style>
