package com.example.kursovaya;

import com.example.kursovaya.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Класс конфигурации для Web Security.
 *
 * @author Валентина Изотова
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserService userService;

    /**
     * Определяет bean для кодировщика паролей.
     *
     * @return Экземпляр BCryptPasswordEncoder, который будет использоваться для кодирования пароля.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настраивает цепочку фильтров безопасности для указанного экземпляра безопасности HTTP.
     *
     * @param http Экземпляр безопасности HTTP для настройки.
     * @return Сконфигурированный экземпляр SecurityFilterChain.
     * @throws Exception если при настройке цепочки фильтров безопасности возникла ошибка.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().requestMatchers("/").permitAll();
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                //Доступ только для не зарегистрированных пользователей
                .requestMatchers("/registration").permitAll()
                //Доступ только для пользователей с ролью Администратор
                .requestMatchers("/doctor/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                //Доступ разрешен всем пользователей
                .requestMatchers("/", "/resources/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/appoint/**").permitAll()
                .requestMatchers("/patient/**").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                //Перенарпавление на главную страницу после успешного входа
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
        return http.build();
    }

    /**
     * Настраивает глобальный AuthenticationManagerBuilder на использование указанного UserService и кодировщика паролей.
     *
     * @param auth Экземпляр AuthenticationManagerBuilder для настройки.
     * @throws Exception если при настройке AuthenticationManagerBuilder произошла ошибка.
     */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}

