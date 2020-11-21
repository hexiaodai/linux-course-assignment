package websocket;

import com.alibaba.fastjson.JSONObject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class Chat {

    //记录当前在线连接数。
    private static int onlineCount = 0;
    private static ConcurrentHashMap<Session, ChatModel> webSocketSet = new ConcurrentHashMap<Session, ChatModel>();

    @OnWebSocketConnect
    public void connected(Session session) {
        System.out.println("=====================");
        ChatModel chat = new ChatModel();
        webSocketSet.put(session, chat);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为: " + getOnlineCount());
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        webSocketSet.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) {
        if(session.isOpen()) {
            ChatModel chat = JSONObject.parseObject(message, ChatModel.class);
            webSocketSet.put(session, chat);
            sendMsgToUser(chat);
        } else {
            System.out.println("session open error");
        }
    }

    private void sendMsgToUser(ChatModel chat) {
        System.out.println("from: " + chat.getUname());
        System.out.println("to: " + chat.getCname());
        System.out.println("content: " + chat.getContent());
        chat.setDate(getNowTime());
        try {
            String chatStr = JSONObject.toJSONString(chat);
            System.out.println(chatStr);
            for (Session _sess : webSocketSet.keySet()) {
                _sess.getRemote().sendString(chatStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getNowTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static synchronized void addOnlineCount() {
        Chat.onlineCount ++;
    }

    public static synchronized int getOnlineCount() {
        return Chat.onlineCount;
    }
}
