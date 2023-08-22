package com.example.kursovaya.Repositories;

import com.example.kursovaya.Tables.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * @author Валентина Изотова
 *
 * Интерфейс репозитория для управления сущностями Пациент.
 *
 * Этот интерфейс расширяет JpaRepository, предоставляя все операции CRUD для сущностей Пациент.
 */
public interface PatientRepository extends JpaRepository<Patient, Long>{
    /**
     * Извлекает список сущностей Пациент, удовлетворяющих указанному ключевому слову.
     *
     * @param keyword
     * @return список сущностей пациент
     */
    @Query("SELECT p FROM Patient p WHERE CONCAT(p.id, ' ', lower(p.name), ' ',lower( p.birthday), ' ', lower(p.medPolNum), " +
            "' ', lower(p.birthday), ' ', lower(p.passport), ' ', lower(p.address), ' ', p.phoneNum) LIKE %?1%")
    List<Patient> search(String keyword);
    /**
     * Извлекает сущность Пациент по условию.
     *
     * @param keyword
     * @return сущность пациент
     */
    @Query("SELECT p FROM Patient p WHERE p.phoneNum = ?1")
    Patient searchOne(String keyword);
    /**
     * Извлекает сущность Пациент по условию.
     *
     * @param name
     * @return сущность пациент
     */
    @Query("select p from Patient p where lower(p.name) = lower(?1)")
    Patient patient(String name);
    /**
     * Извлекает список всех сущностей представленных в таблице Пациент.
     *
     * @return список сущностей пациент
     */
    @Query("select p from Patient p")
    List<Patient> searchPatient();
}
