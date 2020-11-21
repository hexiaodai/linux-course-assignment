Vue.component("popup", {
  name: "Popup",
  data: () => {
    return {
      avatars: [
        "./static/img/0.PNG", "./static/img/1.PNG", "./static/img/2.PNG",
        "./static/img/3.PNG", "./static/img/4.PNG", "./static/img/5.PNG",
        "./static/img/6.GIF", "./static/img/7.GIF"
      ]
    }
  },
  template: `
  <div class="c-avatar">
    <ul>
      <li v-for="avatar in avatars" :key="avatar" @click="onClick(avatar)">
        <img width="100" height="100" :src="avatar"></img>
      </li>
    </ul>
  </div>`,
  methods: {
    onClick(avatar) {
      this.$emit("selavatar", avatar);
    }
  }
})
