Vue.component("login", {
  name: "Login",
  data: () => {
    return {
      uname: null,
      cname: null
    }
  },
  template: `
  <div class="login">
    <p class="login-title">欢迎。</p>
    <input class="login-uname" type="text" placeholder="你的名字" v-model="uname"></input>
    <input class="login-cname" type="text" placeholder="朋友的名字" v-model="cname"></input>
    <button class="login-btn" @click="onLogin">连接</button>
  </div>`,
  methods: {
    onLogin() {
      this.$emit("input", { uname: this.uname, cname: this.cname });
    }
  }
})
