package api.service;

import api.Models.*;
import api.db.DbUtil;
import cn.hutool.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatService {
    public ArrayList getChats(String uname, String cname) throws SQLException {
        ArrayList list = new ArrayList();

        Connection conn = DbUtil.getConn();
        PreparedStatement preparedStatement;
        ResultSet res;

        preparedStatement = conn.prepareStatement("SELECT uname, cname, content, date, uavatar, cavatar FROM chat WHERE uname = ? and cname = ?");
        preparedStatement.setString(1, uname.trim());
        preparedStatement.setString(2, cname.trim());
        res = preparedStatement.executeQuery();

        while(res.next()) {
            ChatModel chat = new ChatModel();
            chat.setUname(res.getString(1));
            chat.setCname(res.getString(2));
            chat.setContent(res.getString(3));
            chat.setDate(res.getString(4));
            chat.setUavatar(res.getString(5));
            chat.setCavatar(res.getString(6));

            JSONObject obj = new JSONObject(chat);
            list.add(obj);
        }
        return list;
    }

    public boolean delChat(String content, String date) throws SQLException {
        Connection conn = DbUtil.getConn();
        PreparedStatement preparedStatement;

        preparedStatement = conn.prepareStatement("update chat set status = ? WHERE content = ? and date = ?");
        preparedStatement.setString(1, "-1");
        preparedStatement.setString(2, content);
        preparedStatement.setString(3, date);
        int res = preparedStatement.executeUpdate();
        return res > 0;
    }

    public boolean addChat(ChatModel chat) throws SQLException {
        Connection conn = DbUtil.getConn();
        PreparedStatement preparedStatement;

        preparedStatement = conn.prepareStatement("INSERT INTO chat(cname, uname, content, date, cavatar, uavatar) VALUES(?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, chat.getUname());
        preparedStatement.setString(2, chat.getCname());
        preparedStatement.setString(3, chat.getContent());
        preparedStatement.setString(4, chat.getDate());
        preparedStatement.setString(5, chat.getUavatar());
        preparedStatement.setString(6, chat.getCavatar());
        int res = preparedStatement.executeUpdate();
        return res > 0;
    }
}
