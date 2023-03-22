package com.salamych.cinema.api.controllers;

import spark.Request;
import spark.Response;
import spark.Route;

public final class ScheduleController extends CommonController {

    // тестовый роут
    public final Route test;

    // конструктор
    public ScheduleController() {
        this.test = (Request req, Response res) -> {
            return ok();
        };
    }
}
