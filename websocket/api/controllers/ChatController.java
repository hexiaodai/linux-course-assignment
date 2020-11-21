package api.controllers;

import api.Models.*;
import api.service.*;
import spark.Route;
import java.util.ArrayList;
import java.util.Set;

import static spark.Spark.*;

public class ChatController {
    public static Route getContent = (request, response) -> {
        String uname = request.queryParams("uname");
        String cname = request.queryParams("cname");
        ArrayList list = new ArrayList();

        if(uname != null && cname != null) {
            ChatService chatService = new ChatService();
            list = chatService.getChats(uname, cname);
        }

        return list;
    };

    public static Route delContent = (request, response) -> {
        String content = request.queryParams("content");
        String date = request.queryParams("date");
        System.out.println(content);
        System.out.println(date);
        boolean succee = false;

        if(content != null && date != null) {
            ChatService chatService = new ChatService();
            succee = chatService.delChat(content, date);
        }

        return succee;
    };

    public static Route addContent = (request, response) -> {
        boolean succee = false;
        ChatModel chat = new ChatModel();
        chat.setUname(request.queryParams("uname"));
        chat.setCname(request.queryParams("cname"));
        chat.setContent(request.queryParams("content"));
        chat.setDate(request.queryParams("date"));
        chat.setUavatar(request.queryParams("uavatar"));
        chat.setCavatar(request.queryParams("cavatar"));

        System.out.println(request.params("uname"));

        if(chat.getUname() != null && chat.getCname() != null) {
            ChatService chatService = new ChatService();
            succee = chatService.addChat(chat);
        }

        return succee;
    };

    public static void init() {
        get("/getContent", getContent);
        delete("/delContent", delContent);
        post("/addContent", addContent);
    }
}
