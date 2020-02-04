<template>
  <div class="center">
    <h1>spring boot cloud ms security</h1>
    <p>shopId: {{ resultInfo.id }}</p>
    <p>productId: {{ resultInfo.productId }}</p>
    <button class="btn" @click="handleGetResult">GET</button>
    <button class="btn logout" @click="logout">logout</button>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      resultInfo: {
        id: "",
        productId: ""
      }
    };
  },
  methods: {
    handleGetResult() {
      this.$axios({
        method: "GET",
        url: "http://localhost:8080/api/order/orders/1"
      })
        .then(res => {
          if (res.status === 200) {
            this.resultInfo.id = res.data.id;
            this.resultInfo.productId = res.data.productId;
          }
        })
        .catch(err => {
          console.log(2, err);
        });
    },
    logout() {
      this.$axios({
        method: "GET",
        url: "http://localhost:8080/auth/logout"
      }).then(res => {
        if (res.status === 200) {
          alert("退出成功");
          this.$router.push("/");
        }
      });
    }
  }
};
</script>

<style lang="stylus" scope>
.center {
 margin-left: calc((100% - 35%) /2)
}
.btn {
 width: 100px
 height: 50px
}
.logout {
 margin-left: 20px
}
</style>
