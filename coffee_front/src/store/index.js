import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);
function createPersistedState() {
    return (store) => {
        // 从 localStorage 中读取状态并初始化
        const persistedState = localStorage.getItem('vuex');
        if (persistedState) {
            store.replaceState(JSON.parse(persistedState));
        }

        // 订阅状态变化并将其存储到 localStorage
        store.subscribe((mutation, state) => {
            localStorage.setItem('vuex', JSON.stringify(state));
        });
    };
}

const persistedState = createPersistedState();

export default new Vuex.Store({
    state: {
        itemsQuantity: {}, // 用于存储每个 item 的数量，格式为 { id: quantity }
        orders: [], // 用于存储临时订单
        address:{},
        userId: -1, // 用于存储用户ID
    },
    mutations: {
        setAddress(state, address) {
            state.address = address;
        },
        setUserId(state, userId) {
            state.userId = userId;
        },
        setItemQuantity(state, { id, quantity }) {
            Vue.set(state.itemsQuantity, id, quantity);
        },
        setAllItemsQuantityToZero(state) {
            Object.keys(state.itemsQuantity).forEach(id => {
                Vue.set(state.itemsQuantity, id, 0);
            });
        },
        addOrder(state, order) {
            state.orders.push(order);
        },
        clearAllOrders(state) {
            state.orders = [];
        },
        updateOrder(state, { index, order }) {
            if (index >= 0 && index < state.orders.length) {
                Vue.set(state.orders, index, order);
            }
            if (order.selfQuantity === 0) {
                state.orders.splice(index, 1);
            }
        },
        removeOrder(state, index) {
            state.orders.splice(index, 1);
        }
    },
    getters: {
        getTotalPrice: (state) => {
            let totalPrice = 0;
            for (let i = state.orders.length - 1; i >= 0; i--) {
                const order = state.orders[i];
                totalPrice += order.newPrice * order.selfQuantity;
            }
            return totalPrice;
        },
        getAddress: (state) => state.address,
        getUserId: (state) => state.userId, // 获取 userId
        getRecentOrderByItemId: (state) => (itemId) => {
            for (let i = state.orders.length - 1; i >= 0; i--) {
                const order = state.orders[i];
                if (order.id === itemId) {
                    return { index: i, order: order };
                }
            }
            return null;
        },
        getAllItemsQuantity: (state) => {
            let allCount = 0;
            Object.keys(state.itemsQuantity).forEach(id => {
                allCount += state.itemsQuantity[id];
            });
            return allCount;
        },
        getItemQuantity: (state) => (id) => {
            return state.itemsQuantity[id] || 0;
        },
        getAllOrders: (state) => {
            return state.orders;
        },
        getItemsQuantity: (state) => {
            return state.itemsQuantity;
        }
    },
    
    plugins: [persistedState] // 使用插件
});
