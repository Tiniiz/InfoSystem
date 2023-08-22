package com.example.kursovaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс для приложения Spring Boot.
 *
 * @author Валентина Изотова
 */
@SpringBootApplication
public class KursovayaApplication {

    /**
     * Точка входа для приложения Spring Boot.
     *
     * @param args аргументы командной строки, передаваемые приложению
     */
    public static void main(String[] args) {
        SpringApplication.run(KursovayaApplication.class, args);
    }

}
