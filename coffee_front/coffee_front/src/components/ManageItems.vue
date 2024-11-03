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

        <el-button class="add-button" icon="el-icon-plus"   @click="openCategoryDialog"> Add Category</el-button>
      </div>
    </div>

    <div class="content">
      <h1>{{ resultsMessage }}</h1>
      <el-button  class="add-button" icon="el-icon-plus"  @click="openProductDialog()">Add Coffee</el-button>
      <div v-for="(category, index1) in filteredCategories" :key="category.name">
        <h2 :ref="'category-' + index1" style="padding: 20px">{{ category.name }}</h2>

        <div class="item-grid">
          <div v-for="(item, index2) in category.items" :key="index2" class="item">
            <el-card class="card" :body-style="{ padding: '0px' }">
              <el-image
                  style="height: 200px; transition: transform 0.3s;"
                  :src="item.image"
                  alt="item.name"
                  class="card-image"
                  @click="editItem(item)"
              ></el-image>
              <div style="padding: 15px; text-align:left; margin-left: 20px">
                {{ item.name }}
              </div>
              <div style="margin: 30px 10px 20px;" class="card-bottom">
                <div>${{ item.price.toFixed(2) }}</div>
                <div class="manage-buttons">
                  <el-button type="primary" icon="el-icon-edit" @click="editItem(item)">EDIT</el-button>
                  <el-button type="danger"  icon="el-icon-delete" @click="deleteItem(item.id)">DELETE</el-button>
                </div>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>


    <el-dialog :visible.sync="categoryDialogVisible" title="Add Category">
      <el-input v-model="newCategoryName" placeholder="Category Name"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="categoryDialogVisible = false">cancel</el-button>
        <el-button type="primary" @click="addCategory">ok</el-button>
      </span>
    </el-dialog>


    <el-dialog :visible.sync="productDialogVisible" title="Add/Edit Coffee">
      <el-input style="margin-top: 10px" v-model="addItem.name" placeholder="Coffee Name"></el-input>
      <el-input style="margin-top: 10px" v-model="addItem.price" placeholder="Price" type="number"></el-input>
      <el-input style="margin-top: 10px" v-model="addItem.image" placeholder="ImgUrl"></el-input>
      <el-select    value-key="id" style="margin-top: 10px" v-model="addItem.categoryId" placeholder="Select Category"
                 @change="handleCategoryChange"
      >
        <el-option
            style="margin-top: 10px"
            v-for="category in tempCategories"
            :key="category.id"
            :label="category.name"
            :value="category.id"
        >

        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="productDialogVisible = false">cancel</el-button>
        <el-button type="primary" @click="saveProduct">ok</el-button>
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
      sCategory : {id :'',name :''},
      categories: [],
      tempCategories : [],
      filteredCategories: [],
      dialogVisible: false,

      selectedItem: { name: '', price: '', categoryId: null, image: '' },
      addItem: { name: '', price: '', categoryId: null, image: '' },


      categoryDialogVisible: false,
      productDialogVisible: false,
      newCategoryName: '',
    };
  },
  computed: {
    resultsMessage() {
      return this.searchQuery ? `Search results for "${this.searchQuery}"` : '';
    },
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await this.$axios.get(`${this.$httpUrl}/categories/getAll`);
        if (response.data.code === 200) {

          this.categories = response.data.data;
          this.tempCategories = this.categories;

          this.filteredCategories = this.categories;
          this.flushQuantity();
        } else {
          console.error(response.data.message);
        }
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    },
    async fetchProducts() {
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
        console.error('Error fetching products:', error);
      }
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
    filterItems() {
      this.filteredCategories = this.categories
          .map(category => ({
            ...category,
            items: category.items.filter(item =>
                item.name.toLowerCase().includes(this.searchQuery.toLowerCase())
            ),
          }))
          .filter(category => category.items.length > 0);
    },
    selectCategory(category) {
      this.selectedCategory = category;
      this.searchQuery = '';
      this.filterItems();

      const categoryIndex = this.categories.findIndex(cat => cat.name === category);
      const categoryRef = this.$refs[`category-${categoryIndex}`];
      if (categoryRef && categoryRef.length > 0) {
        categoryRef[0].scrollIntoView({ behavior: 'smooth', block: 'center' });
      }
    },
    openCategoryDialog() {
      this.newCategoryName = '';
      this.categoryDialogVisible = true;
    },
    editItem(item) {

      this.addItem = {
        id: item.id,
        name: item.name,
        price: item.price,
        image: item.image,
        categoryId: item.categoryId,
      };

      this.productDialogVisible = true;
    },
    async addCategory() {
      try {
        const response = await this.$axios.post(`${this.$httpUrl}/categories/add`, { name: this.newCategoryName });
        if (response.data.code === 200) {
          this.fetchCategories();
          this.fetchProducts();
          this.categoryDialogVisible = false;
        }
      } catch (error) {
        console.error('Error adding category:', error);
      }
    },
    openProductDialog() {
      this.addItem = { name: '', price: '', categoryId: null, image: '' };
      this.productDialogVisible = true;
    },
    handleCategoryChange(value) {

      console.log("Selected Category ID:", value);

    },
    async saveProduct() {
      try {
        if (this.addItem.id) {
          await this.$axios.put(`${this.$httpUrl}/coffees/update`, this.addItem);
        } else {
          await this.$axios.post(`${this.$httpUrl}/coffees/add`, this.addItem);
        }
        this.productDialogVisible = false;
        this.fetchProducts();
      } catch (error) {
        console.error('Error saving product:', error);
      }
    },
    async deleteItem(itemId) {
      try {
        await this.$axios.delete(`${this.$httpUrl}/coffees/delete/${itemId}`);
        this.fetchProducts();
      } catch (error) {
        console.error('Error deleting item:', error);
      }
    },
  },
  mounted() {
    this.fetchCategories();
    this.fetchProducts();
  },
};
</script>

<style scoped>
.search { font-size: 16px; font-weight: bold; }
.main { padding-top: 200px; display: flex; max-width: 1200px; margin: 0 auto; }
.sidebar { width: 250px; position: fixed; top: 200px; height: calc(100% - 200px); overflow-y: auto; }
.categories { margin-top: 20px; }
.categories div { cursor: pointer; margin-top: 30px; color: #ADADAD; font-size: 20px; font-weight: lighter; }
.categories div.active, .categories div:hover { font-weight: bold; color: #C3A37C; text-decoration: underline; }
.detail-content { font-weight: bold; font-size: 22px; }
.content { flex-grow: 1; margin-left: 330px; font-weight: bold; font-size: 22px; }
.item-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 15px; }
.item { padding: 10px; text-align: center; }
.add-button { background-color: #C7A17A; border: none; border-radius: 50%; width: 40px; height: 40px; font-size: 35px; cursor: pointer; }
.card-bottom { padding: 0 20px; display: flex; justify-content: space-between; }
.card { cursor: pointer; }
.card:hover .card-image { transform: scale(1.05); }
.manage-buttons { display: flex; gap: 10px; }
.dialog-footer { display: flex; justify-content: flex-end; margin-top: 20px; }
.add-button {
  background-color: #42b983;
  color: white;
  border-radius: 5px;
  padding: 10px 20px;
  width: 100%;
  margin-top: 15px;
  margin-right: 10px;
  font-weight: bold;
  font-size: 18px;
}
</style>
