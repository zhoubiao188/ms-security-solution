<template>
  <div class="catainer">
    <div class="login">
      <h1>标准登录页面</h1>
      username: <input v-model="auth.username" type="text" /> <br />
      password: <input v-model="auth.password" type="password" /> <br />
      <button @click="handleLogin">登录</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      auth: {
        username: "admin",
        password: "123456"
      }
    };
  },
  methods: {
    handleLogin() {
      let params = {
        username: this.auth.username,
        password: this.auth.password
      };
      this.$axios({
        method: "POST",
        url: "http://localhost:8080/auth/login",
        data: params,
        headers: {
          "Content-Type": "application/json"
        }
      })
        .then(res => {
          if (res.status === 200) {
            this.$router.push("/index");
          }
        })
        .catch(err => {
          alert("登录失败:" + err);
        });
    }
  }
};
</script>

<style lang="stylus" scope>
.catainer {
  margin-left: calc((100% - 20%) /2)
}
input {
  width: 200px
  height: 30px
}
button {
  width: 100px
  height: 30px
  margin-top: 10px
}
</style>
