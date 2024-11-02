package com.polarbookshop.catalogservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//Идентифицирует класс, определяющий обработчики для конечных точек REST/HTTP.
public class HomeController {

    @GetMapping("/")//Обрабатывает запросы GET к корневой конечной точке
    public String getGreeting() {
        return "Welcome to the book catalog!";
    }

}
