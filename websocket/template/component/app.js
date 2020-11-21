const App = new Vue({
  el: "#app",
  data: {
    ws: null,
    // baseApi: "http://localhost:9001",
    // wsUrl: "ws://localhost:9000/user",
    baseApi: "http://106.15.9.247:9001",
    wsUrl: "ws://106.15.9.247:9003/user",
    search: null,
    visible: false,
    contents: [],
    userObj: {
      uname: null,
      cname: null,
      content: null,
      date: null,
      uavatar: "./static/img/3.PNG",
      cavatar: "./static/img/7.GIF"
    },
    msgList: [],
  },
  computed: {
    isLogin() {
      return this.userObj.uname && this.userObj.cname
    }
  },
  watch: {
    search(newVal, oldVal) {
      this.contents = []
      this.msgList.forEach(e => {
        if ((e.content).search(newVal) != -1) {
          this.contents.push(e)
        }
      });
      if (!newVal) {
        this.contents = []
      }
    }
  },
  methods: {
    isCname(item) {
      return item.cname === this.userObj.cname
    },

    async onLogin(args) {
      if(args.uname && args.cname) {
        this.userObj.uname = args.uname
        this.userObj.cname = args.cname
        // 设置cookie
        //this.setCookie("uname", this.userObj.uname, 30)
        //this.setCookie("cname", this.userObj.cname, 30)
        // this.ws = new WebSocket(this.wsUrl)
        this.onConnect()
        await this.getContent()
      } else {
        alert("请输入...")
      }
    },

    setCookie(name, value, day) {
      let d = new Date();
      d.setTime(d.getTime()+(day*24*60*60*1000));
      let expires = "expires="+d.toGMTString();
      document.cookie = name + "=" + value + "; " + expires;
    },

    getCookie(name) {
      let _name = name + "="
      let ca = document.cookie.split(';');
      for(let i=0; i<ca.length; i++) 
      {
        let c = ca[i].trim();
        if (c.indexOf(_name)==0) {
          return c.substring(_name.length,c.length)
        }
      }
      return null
    },

    onAvatar() {
      this.visible = !this.visible
    },
    onSelAvatar(avatar) {
      this.userObj.uavatar = avatar
      this.visible = false
    },
    async onDele(e, i) {
      // 删除会话记录
      const params = { "content": e.content, "date": e.date }
      const res = await axios.delete(this.baseApi + "/delContent", { params })
      if (res.status === 200) {
        this.msgList = this.msgList.filter(_e => {
          return !(_e.content === e.content && _e.date === e.date)
        })
        this.contents.splice(i, 1)
      } else {
        alert("删除失败：", e);
      }
    },
    onSearch() {

    },
    onConnect() {
      if (!this.isLogin) {
        alert("请登录...");
        return false
      }
      this.ws.onopen = () => {
        this.ws.send(JSON.stringify(this.userObj));
      }
      this.ws.onmessage = async (res) => {
        if (res.data) {
          let uObj = JSON.parse(res.data)
          console.log(uObj)
          this.msgList.push(uObj)
          this.userObj.uavatar = uObj.uavatar
          this.userObj.cavatar = uObj.cavatar
          // 将消息存入数据库
          const addRes = await axios.post(this.baseApi + "/addContent", "uname="+uObj.uname+"&cname="+uObj.cname+"&content="+uObj.content+"&date="+uObj.date+"&uavatar="+uObj.uavatar+"&cavatar="+uObj.cavatar)
          if (addRes.status == 200 && addRes.data) {
            console.log("会话入库成功");
          } else {
            console.log("会话入库失败：", addRes.data);
          }
        }
      }
    },

    onSend() {
      if (this.isLogin) {
        console.log(JSON.stringify(this.userObj))
        this.ws.send(JSON.stringify(this.userObj))
      } else {
        alert("发送失败")
      }
    },

    // 获取用户会话历史记录
    async getContent() {
      const params = { uname: this.userObj.uname, cname: this.userObj.cname }
      const res = await axios.get(this.baseApi + "/getContent", { params })
      if (res.status === 200) {
        this.msgList = res.data
      }
    }
  },
  async created() {
    // 读取Cookie
    //this.userObj.uname = this.getCookie("uname")
    //this.userObj.cname = this.getCookie("cname")

    this.ws = new WebSocket(this.wsUrl)
    // await this.getContent()
  },

  mounted() {
  }
})
