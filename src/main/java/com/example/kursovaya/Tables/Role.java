package com.example.kursovaya.Tables;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
/**
 * Класс представляет роль пользователя в системе.
 *
 * @author Валентина Изотова
 */
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    /**
     * Идентификатор роли.
     */
    @Id
    private Long id;
    /**
     * Название роли.
     */
    private String name;
    /**
     * Список пользователей, у которых данная роль назначена.
     * Ссылка на класс-сущность Пользователь {@link User}
     */
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    /**
     * Конструктор по умолчанию.
     */
    public Role() {
    }
    /**
     * Конструктор с одним параметром.
     * @param id Идентификатор роли.
     */
    public Role(Long id) {
        this.id = id;
    }
    /**
     * Конструктор с двумя параметрами.
     * @param id Идентификатор роли.
     * @param name Название роли.
     */
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    /**
     * Получить идентификатор роли.
     * @return Идентификатор роли.
     */
    public Long getId() {
        return id;
    }
    /**
     * Установить идентификатор роли.
     * @param id Идентификатор роли.
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Получить название роли.
     * @return Название роли.
     */
    public String getName() {
        return name;
    }
    /**
     * Установить название роли.
     * @param name Название роли.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Получить список пользователей, у которых данная роль назначена.
     * @return Список пользователей.
     */
    public Set<User> getUsers() {
        return users;
    }
    /**
     * Установить список пользователей, у которых данная роль назначена.
     * @param users Список пользователей.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    /**
     * Получить название роли, которое будет использоваться в качестве аутентификационной метки.
     * @return Название роли.
     */
    @Override
    public String getAuthority() {
        return getName();
    }
}