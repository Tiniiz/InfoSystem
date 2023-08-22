package com.example.kursovaya.Tables;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
/**
 *  Класс-сущность, представляющий пользователя.
 *
 *  @author Валентина Изотова
 */
@Entity
@Table(name = "user")
public class User implements UserDetails {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Логин пользователя.
     */
    @Size(min=2, message = "Не меньше 5 знаков")
    private String username;
    /**
     * Пароль пользователя.
     */
    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;
    /**
     * Подтверждение пароля пользователя.
     */
    @Transient
    private String passwordConfirm;
    /**
     * Роли пользователя.
     * Ссылка на класс-сущность роли: {@link Role}
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    /**
     * Конструктор по умолчанию.
     */
    public User() {
    }
    /**
     * Получить уникальный идентификатор пользователя.
     *
     * @return уникальный идентификатор пользователя.
     */
    public Long getId() {
        return id;
    }
    /**
     * Установить уникальный идентификатор пользователя.
     *
     * @param id уникальный идентификатор пользователя
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Получить логин пользователя.
     *
     * @return логин пользователя
     */
    @Override
    public String getUsername() {
        return username;
    }
    /**
     * Проверить не истек ли срок действия аккаунта.
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Проверить не заблокирован ли аккаунт.
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Проверить не истек ли срок действия учетной записи.
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Проверить активирован ли аккаунт.
     *
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    /**
     * Установить логин пользователя.
     *
     * @param username логин пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Получить роли пользователя.
     *
     * @return роли пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
    /**
     * Получить пароль пользователя.
     *
     * @return пароль пользователя
     */
    @Override
    public String getPassword() {
        return password;
    }
    /**
     * Установить пароль пользователя.
     *
     * @param password пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Получить подтверждение пароля пользователя.
     *
     * @return подтверждение пароля пользователя
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    /**
     * Установить подтверждение пароля пользователя.
     *
     * @param passwordConfirm подтверждение пароля пользователя
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    /**
     * Получить роли пользователя.
     *
     * @return роли пользователя
     */
    public Set<Role> getRoles() {
        return roles;
    }
    /**
     * Установить роли пользователя.
     *
     * @param roles роли пользователя
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}