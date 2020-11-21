package api;

import api.controllers.*;
import static spark.Spark.*;

public class Start {
    public static void main(String[] args) {
        staticFiles.location("/public/start");
        port(9001);

        before("/*",(request, response) -> {
            response.header("Content-Type", "application/json");
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Cache-Control", "no-cache");
        });

        ChatController.init();
    }
}
