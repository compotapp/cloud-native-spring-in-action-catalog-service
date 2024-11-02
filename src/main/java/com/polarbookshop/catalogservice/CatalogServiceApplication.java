package com.polarbookshop.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication это ярлык, включающий три разных аннотации:
//@Configuration помечает класс как источник определений bean-компонентов.
//@ComponentScan включает сканирование компонентов для автоматического поиска и регистрации bean-компонентов в контексте Spring.
//@EnableAutoConfiguration включает возможности автоматической настройки, предлагаемые Spring Boot.
@SpringBootApplication//Определяет класс конфигурации Spring, и запускает сканирование компонентов и автоматическую настройку Spring Boot.
public class CatalogServiceApplication {

    public static void main(String[] args) {//Метод, используемый для запуска приложения. Он регистрирует текущий класс для запуска на этапе начальной загрузки приложения.
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

}
