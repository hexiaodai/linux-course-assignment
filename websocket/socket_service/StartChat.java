package websocket;

import static spark.Spark.*;

public class StartChat {
    public static void main(String[] args) {
        port(9003);

        webSocket("/user", User.class);
        init();
    }
}
