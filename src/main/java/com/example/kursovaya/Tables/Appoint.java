package com.example.kursovaya.Tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс-сущность, представляющий Запись.
 *
 * @author Валентина Изотова
 */
@Setter
@Getter
@Entity
@Table(name = "appoint")
public class Appoint {

    /**
     * Идентификатор записи.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Ссылка на врача через связь многие к одному.
     * Ссылка на класс сущности Врачи: {@link Doctor}
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    /**
     * Ссылка на пациента через связь многие к одному.
     * Ссылка на класс сущности Пациенты: {@link Patient}
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    /**
     * Дата записи.
     */
    @Column(name = "date_time")
    private String dateTime;
}
