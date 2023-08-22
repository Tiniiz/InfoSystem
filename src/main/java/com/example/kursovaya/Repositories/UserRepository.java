package com.example.kursovaya.Repositories;

import com.example.kursovaya.Tables.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Валентина Изотова
 *
 * Интерфейс репозитория для управления сущностями Пользователь.
 *
 * Этот интерфейс расширяет JpaRepository, предоставляя все операции CRUD для сущностей Пользователь.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Извлекает сущность Пользователь с заданным именем пользователя.
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
