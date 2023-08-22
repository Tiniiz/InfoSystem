package com.example.kursovaya.Repositories;

import com.example.kursovaya.Tables.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Валентина Изотова
 *
 * Интерфейс репозитория для управления сущностями Роль.
 *
 * Этот интерфейс расширяет JpaRepository, предоставляя все операции CRUD для сущностей Роль.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}