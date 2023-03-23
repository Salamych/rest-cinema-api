package com.salamych.cinema.api;

import spark.Spark;
import static spark.Spark.*;

import com.salamych.cinema.api.controllers.ScheduleController;
import com.salamych.cinema.api.models.ScheduleModel;
import com.salamych.cinema.api.storages.FileStorage;

import static com.salamych.cinema.api.transformers.JsonTransformer.JSON;

public class App {

    public final static String JSON_MIME_TYPE = "application/json";

    private final static String SERVER_HOST = "127.0.0.1";
    private final static int SERVER_PORT = 8808;

    private final static String DEFAULT_FILE = "schedule.tsv";

    public static void main(String[] args) {

        // создание файлового хранилища
        final FileStorage<ScheduleModel> storage = new FileStorage<ScheduleModel>(
                args != null && args.length == 1 ? args[0] : DEFAULT_FILE,
                ScheduleModel.class
        );

        // чтение файла данных
        storage.readAll();

        // создание контроллера
        final ScheduleController controller = new ScheduleController();

        // конфигурация сервера
        ipAddress(SERVER_HOST);
        port(SERVER_PORT);

        // объявление роутов
        path("/api", () -> {
            get("/test", controller.test, JSON);
        });
        after((req, res) -> {
            res.type(JSON_MIME_TYPE);
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // остановка сервера
                Spark.stop();
                // сохранение данных в файл
                storage.writeAll();
            }
        });

    }

}
