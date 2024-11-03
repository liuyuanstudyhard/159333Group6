<template>
  <div class="order-management-container">
    <el-card class="order-management-card">
      <div class="header">
        <!-- Back button icon -->
        <el-button icon="el-icon-back" @click="goHome" class="back-button" />
        <!-- Order Management Title -->
        <h2 class="page-title">Order Management</h2>
      </div>

      <!-- Order list -->
      <el-table
          :data="orders"
          stripe
          style="width: 100%"
          v-loading="loading"
          empty-text="No orders found"
      >
        <!-- Order ID -->
        <el-table-column prop="orderId" label="Order ID" width="200" />
        <!-- User ID -->
        <el-table-column prop="userId" label="User ID" width="280" />
        <!-- Status -->
        <el-table-column prop="orderStatus" label="Status" width="280" />
        <!-- Date -->
        <el-table-column prop="date" label="Date" width="280" />
        <!-- Time -->
        <el-table-column prop="time" label="Time" width="280" />
        <!-- Location -->
        <el-table-column prop="address" label="address" width="280" />
        <!-- Specific Location -->
        <el-table-column prop="price" label="price" width="280" />

        <el-table-column prop="productDetails" label="productDetails" width="400" />
        <!-- Action Column -->
        <el-table-column label="Actions" width="180">
          <template slot-scope="scope">
            <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="confirmDeleteOrder(scope.row.orderId)"
            >Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      orders: [], // Order list
      loading: false, // Loading state
    };
  },
  methods: {
    // Fetch order list
    fetchOrders() {
      this.loading = true;
      const userId = this.$store.getters.getUserId; // Get the logged-in user ID
      axios
          .get(`${this.$httpUrl}/order/user/${userId}`) // Request orders for the user
          .then((response) => {
            this.orders = response.data; // Ensure all order fields are included
            this.loading = false;
          })
          .catch((error) => {
            console.error("Error fetching orders:", error);
            this.$message.error("Failed to load orders");
            this.loading = false;
          });
    },
    // Confirm delete order
    confirmDeleteOrder(orderId) {
      this.$confirm("This action will permanently delete the order. Continue?", "Warning", {
        confirmButtonText: "Yes",
        cancelButtonText: "No",
        type: "warning",
      })
          .then(() => {
            this.deleteOrder(orderId);
          })
          .catch(() => {
            this.$message.info("Delete canceled");
          });
    },
    // Delete order action
    deleteOrder(orderId) {
      axios
          .delete(`${this.$httpUrl}/order/delete/${orderId}`)
          .then(() => {
            this.$message.success("Order deleted successfully");
            this.fetchOrders(); // Reload orders
          })
          .catch((error) => {
            console.error("Error deleting order:", error);
            this.$message.error("Failed to delete order");
          });
    },

    // Go back to home page
    goHome() {
      this.$router.push('/'); // Redirect to home route
    },
  },
  created() {
    this.fetchOrders(); // Fetch orders on page load
  },
};
</script>

<style scoped>
.order-management-container {
  padding: 20px;
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
  text-align: center; /* Center title */
}

.back-button {
  border: none; /* Hide button border */
  background-color: transparent; /* Transparent button background */
  color: #606266; /* Set icon color */
  padding: 0; /* Remove default button padding */
  font-size: 24px; /* Set icon size */
  margin-right: auto; /* Ensure button is on the left */
}

.page-title {
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}
</style>
