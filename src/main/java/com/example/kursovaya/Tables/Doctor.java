package com.example.kursovaya.Tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Класс-сущность, представляющий Врача.
 *
 * @author Валентина Изотова
 */
@Setter
@Getter
@Entity
public class Doctor {
    /**
     * Идентификатор врача.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Список записей врача, получаемый через связь один ко многим.
     * Ссылка на класс сущности Записи: {@link Appoint}
     */
    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private List<Appoint> appoint;
    /**
     * Имя врача.
     */
    private String name;
    /**
     * Специализация врача.
     */
    private String qualif;
    /**
     * День рождения врача.
     */
    private String birthday;
    /**
     * Номер паспорта врача.
     */
    private String passport;
    /**
     * Адресс врача.
     */
    private String address;
}
