## Linux 小组作业

### 作业一
- [何建民](https://gitee.com/hexiaodai/linux/blob/master/notes.md)

### 作业二
- 点击查看：[数据库备份：mysql_backup](https://gitee.com/wavelets/linux-work/tree/master/mysql_backup)

### 作业三
- 点击查看：目录同步

### 作业四
- 点击查看：[Linux上使用Java实现管道操作](https://gitee.com/wavelets/linux-work/blob/master/stream/Stream.java)

### 作业五
- 点击查看：Linux的IO模型Java channel selector buffer的实现举例

### 作业六
- 点击查看：[Web和WebSocket](https://gitee.com/wavelets/linux-work/tree/master/websocket)
- 实现功能：
    - [端用户之间通过webSocket通讯](https://gitee.com/wavelets/linux-work/tree/master/websocket/socket_service)
    - [保存用户之间的会话记录到数据库](https://gitee.com/wavelets/linux-work/blob/master/websocket/api/controllers/ChatController.java)
    - [获取用户会话记录](https://gitee.com/wavelets/linux-work/blob/master/websocket/api/controllers/ChatController.java)
    - [删除用户会话记录](https://gitee.com/wavelets/linux-work/blob/master/websocket/api/controllers/ChatController.java)
- 前端：
    [调用用户会话、获取用户会话记录、删除用户会话记录、添加用户会话记录API](https://gitee.com/wavelets/linux-work/tree/master/websocket/template)
- API
    - get("/getContent", getContent)：前端通过Axios.js异步调用此接口（传递参数string cname、string uname），实现获取该用户直接的会话记录
    - delete("/delContent", delContent)：前端通过Axios.js异步调用此接口（传递参数string content、string date），实现删除该用户直接的会话记录
    - post("/addContent", addContent)：前端通过Axios.js异步调用此接口（传递参数ChatModel chat），实现添加该用户之间的会话记录