<template>
    <div class="main">
        <div class="sidebar">
            <el-input
                prefix-icon="el-icon-search"
                placeholder="Search Items"
                v-model="searchQuery"
                @input="filterItems"
                class="search"
            />
            <div class="categories">
                <div
                    v-for="category in categories"
                    :key="category.name"
                    @click="selectCategory(category.name)"
                    :class="{ active: selectedCategory === category.name }"
                >
                    {{ category.name }}
                </div>
            </div>
        </div>

        <div class="content">
            <h1>{{ resultsMessage }}</h1>
            <div v-for="(category,index1) in filteredCategories" :key="category.name">
                <h2 :ref="'category-' + index1" style="padding: 20px">{{ category.name }}</h2>
                <div class="item-grid">
                    <div
                        v-for="(item,index2) in category.items"
                        :key=index2
                        class="item"
                    >
                        <el-card class="card" :body-style="{ padding: '0px' }">
                            <el-image style="height: 200px; transition: transform 0.3s;"
                                      :src="item.image" alt="item.name" class="card-image" @click="showDetails(item)"></el-image>
                            <div style="padding: 15px ; text-align:left;margin-left: 20px"  @click="showDetails(item)">{{ item.name }}</div>
                            <div style="margin: 30px 10px 20px;" class="card-bottom">
                                <div>
                                    ${{ item.price.toFixed(2) }}
                                </div>

                                <div>
                                    <button
                                        v-if="item.quantity === 0"
                                        class="add-button"
                                        @click="addItem(index1,index2)"
                                    >
                                        +
                                    </button>
                                    <div

                                        v-else
                                        class="quantity-display"
                                    >
                                        <el-input-number size="large"  v-model="item.quantity" :min="0" @change="changeNum($event,item)"></el-input-number>
                                    </div>
                                </div>
                            </div>

                        </el-card>


                    </div>
                </div>
            </div>
        </div>
        <el-dialog :visible.sync="dialogVisible" :width="'500px'"
                   center>
            <div class="detail-content">
                <el-image style="margin: 0 auto;display: block;width: 400px;height: 300px"
                           :src="selectedItem.image" :alt="selectedItem.name"></el-image>
                <h3 style="padding: 20px 5px 3px 20px">{{ selectedItem.name }}</h3>
                <p style="font-size: 18px;padding-left: 20px">${{ currentPrice }}</p>

                <h3 style="font-size: 20px;padding-left: 20px;padding-top: 50px">Choose Size</h3>
                <div class="sizes">
                    <el-button
                        v-for="size in sizes"
                        :key="size.label"
                        :type="currentSize === size.label ? 'default' : 'text'"
                        @click="selectSize(size)"
                        class="size-button"
                        style="padding: 20px;font-size: 22px;font-weight: bold;width: 120px;border: 1px solid #E6E3E3"
                    >
                        <p style="padding-bottom: 5px">{{ size.label }}</p>  <p style="font-size: 16px;color: #AC9DBE">${{ size.price }}</p>
                    </el-button>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-input-number size="large"  v-model="selectedItem.selfQuantity" :min="1" @change="changeVal($event)"
                ></el-input-number>
                <el-button size="large" @click="addCart">Add To Cart · ${{currentPrice}}</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            searchQuery: '',
            selectedCategory: null,
            categories: [],
            filteredCategories: [],
            dialogVisible: false,
            selectedItem: {
                selfQuantity: 1
            },
            currentSize: 'Small',
            sizes: [
                { label: 'Small', price: 4.90 },
                { label: 'Medium', price: 5.65 },
                { label: 'Large', price: 6.00 }
            ],
            currentPrice: 0,
        };
    },
    watch: {
        dialogVisible(newVal) {
            if (newVal) {
                this.selectedItem.selfQuantity = 1;
            }
        }
    },
    computed: {
        resultsMessage() {
            if (this.searchQuery) {
                return `Search results for "${this.searchQuery}"`;
            }else {
                return '';
            }
        },
    },
    methods: {
        addCart() {
            this.selectedItem.size = this.currentSize;
            this.$store.commit('addOrder',JSON.parse(JSON.stringify(this.selectedItem)))
            this.dialogVisible = false;

            let quantity = this.$store.getters.getItemQuantity(this.selectedItem.id)
            quantity += this.selectedItem.selfQuantity;
            this.$store.commit('setItemQuantity', { id: this.selectedItem.id, quantity: quantity })
            this.flushQuantity();
        },
        getCurrentPrice() {
            const selectedSize = this.sizes.find(size => size.label === this.currentSize);
            let newPrice = selectedSize ? selectedSize.price : 0;
            this.selectedItem.newPrice = newPrice;
            this.currentPrice = (newPrice * (this.selectedItem.selfQuantity ? this.selectedItem.selfQuantity : 1)).toFixed(2);
        },
        changeVal() {
            this.getCurrentPrice()
            this.flushQuantity();
        },
        changeNum(newQuantity,item) {
            const oldQuantity = this.$store.getters.getItemQuantity(item.id);
            const changeValue = newQuantity - oldQuantity ;

            this.$store.commit('setItemQuantity', { id: item.id, quantity: item.quantity });
            let recentOrder = this.$store.getters.getRecentOrderByItemId(item.id);
            recentOrder.order.selfQuantity += changeValue;

            this.$store.commit('updateOrder', {index: recentOrder.index,order: recentOrder.order});
            this.flushQuantity();
        },
        selectSize(size) {
            this.currentSize = size.label;
            this.getCurrentPrice();
        },
        filterItems() {
            this.filteredCategories = this.categories.map(category => {
                return {
                    ...category,
                    items: category.items.filter(item => {
                        return item.name.toLowerCase().includes(this.searchQuery.toLowerCase());
                    }),
                };
            }).filter(category => category.items.length > 0);
        },
        selectCategory(category) {
            this.selectedCategory = category;
            this.searchQuery = '';
            this.filterItems();

            // Scroll to the category
            const categoryIndex = this.categories.findIndex(cat => cat.name === category);
            const categoryRef = this.$refs[`category-${categoryIndex}`];
            if (categoryRef && categoryRef.length > 0) {
                categoryRef[0].scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        },
        addItem(index1,index2) {
            this.showDetails(this.categories[index1].items[index2]);
        },
        showDetails(item) {
            this.selectedItem = item;
            this.dialogVisible = true;
            this.sizes = [
                {label: 'Small', price: item.price},
                {label: 'Medium', price: item.price + 1.5 },
                {label: 'Large', price: item.price + 2 }
            ];
            this.getCurrentPrice()

        },
        flushQuantity() {
            for (let i = 0; i < this.categories.length; i++) {
                let items = this.categories[i].items;
                for (let j = 0; j < items.length; j++) {
                    items[j].quantity = this.$store.getters.getItemQuantity(items[j].id);
                }
            }
            this.$forceUpdate();
        },
        async fetchCoffees() {
            try {
                const response = await this.$axios.get(`${this.$httpUrl}/coffees/getCoffees`);
                if (response.data.code === 200) {
                    this.categories = response.data.data;
                    this.filteredCategories = this.categories;
                    this.flushQuantity();
                } else {
                    console.error(response.data.message);
                }
            } catch (error) {
                console.error('Error fetching coffees:', error); // 捕获并处理请求错误
            }
        }
    },
    mounted() {
        this.filteredCategories = this.categories;
    },
    created() {
        this.fetchCoffees()

    }
};
</script>

<style>
.search {
    font-size: 16px;
    font-weight: bold;
}
.main {
    padding-top: 200px;
    display: flex;
    max-width: 1200px;
    margin: 0 auto;
}
.sidebar {
    width: 250px;
    position: fixed; /* 固定侧边栏 */
    top: 200px; /* 根据页面内容的高度调整 */
    height: calc(100% - 200px); /* 确保侧边栏的高度适应视口 */
    overflow-y: auto; /* 如果内容超出，添加滚动条 */
}
.categories {
    margin-top: 20px;
}
.categories div {
    cursor: pointer;
    margin-top: 30px;
    color: #ADADAD;
    font-size: 20px;
    font-weight: lighter;
}
.categories div.active,.categories div:hover {
    font-weight: bold;
    color: #C3A37C;
    text-decoration: underline;
}
.detail-content {
    font-weight: bold;
    font-size: 22px;
}
.content {
    flex-grow: 1;
    margin-left: 330px;
    font-weight: bold;
    font-size: 22px;
}
.item-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 15px;
}
.item {
    padding: 10px;
    text-align: center;
}
.item img {
    max-width: 100%;
    height: auto;
}
.add-button {
    background-color: #C7A17A;
    border: none;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    font-size: 35px;
    cursor: pointer;
}

.card-bottom {
    padding: 0 20px;
    display: flex;
    justify-content: space-between;
}

.card {
    cursor: pointer;
}
.card:hover .card-image {
    transform: scale(1.05);
}


.sizes {
    margin: 20px 0;
}

.size-button {
    margin: 0 10px;
}
.dialog-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}
</style>
