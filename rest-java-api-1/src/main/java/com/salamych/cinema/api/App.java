package com.salamych.cinema.api;

import spark.Spark;
import static spark.Spark.*;

import com.salamych.cinema.api.controllers.ScheduleController;

import static com.salamych.cinema.api.transformers.JsonTransformer.JSON;

public class App {
    public final static String JSON_MIME_TYPE = "application/json"; 
    
    private final static String SERVER_HOST  = "127.0.0.1";
    private final static int SERVER_PORT  = 8808;
    

    public static void main(String[] args) {
        final ScheduleController controller = new ScheduleController();
        
        // конфигурация сервера
        ipAddress(SERVER_HOST);
        port(SERVER_PORT);
        
        // объявление роутов
        path("/api", ()->{
        get("/test",controller.test,JSON);
        });
        after((req, res)->{
        res.type(JSON_MIME_TYPE);
        });
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run(){
        // остановка сервера
        Spark.stop();
        }
        });
        
    }
    
}
