package com.example.kursovaya.Tables;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * Класс-сущность, представляющий Пациента.
 *
 * @author Валентина Изотова
 */
@Setter
@Getter
@Entity
public class Patient {
    /**
     * Идентификатор пациента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Список записей пациента, получаемый через связь один ко многим.
     * Ссылка на класс сущности Записи: {@link Appoint}
     */
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    public List<Appoint> appoint;
    /**
     * Имя пациента.
     */
    private String name;
    /**
     * Денб рождения пациента.
     */
    private String birthday;
    /**
     * Номер медицинского полиса пациента.
     */
    private String medPolNum;
    /**
     * Номер паспорта пациента.
     */
    private String passport;
    /**
     * Адресс пациента.
     */
    private String address;
    /**
     * Номер телефона пациента.
     */
    private String phoneNum;
}
