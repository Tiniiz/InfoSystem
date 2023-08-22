package com.example.kursovaya;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Класс конфигурации для конфигураций, связанных с MVC.
 *
 * @author Валентина Изотова
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Добавляет контроллер представления для пути "/login" в реестр представлений.
     * Имя представления устанавливается на "auth".
     *
     * @param registry реестр ViewControllerRegistry, который будет содержать контроллер представления.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("auth");
    }
}
