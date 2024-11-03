<template>
    <div>
        <header-component :callFeaturedMethod="callFeaturedMethod"/>
      <template v-if="isAdmin">

        <manage-items ref="manageItems"/>

      </template>
      <template v-else>

      <featured-items ref="featuredItems"/>
      </template>
    </div>
</template>

<script>
import HeaderComponent from "@/components/Header.vue";
import FeaturedItems from "@/components/CoffeeItems.vue";
import ManageItems from "@/components/ManageItems.vue";

export default {
    name: 'HomeView',
  
    components: {ManageItems,FeaturedItems, HeaderComponent},
    methods: {
        callFeaturedMethod() {
           
            this.$refs.featuredItems.flushQuantity();
        }
    },
  computed: {
    isAdmin(){
      const userId = localStorage.getItem('userId');
      return userId === "999";
    }
  }

};
</script>

<style scoped>
h2 {
    color: #42b983;
}
</style>
