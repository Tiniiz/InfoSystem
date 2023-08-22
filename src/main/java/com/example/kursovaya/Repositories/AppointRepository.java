package com.example.kursovaya.Repositories;

import com.example.kursovaya.Tables.Appoint;
import com.example.kursovaya.Tables.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * @author Валентина Изотова
 *
 * Интерфейс репозитория для управления сущностями Запись.
 *
 * Этот интерфейс расширяет JpaRepository, предоставляя все операции CRUD для сущностей Запись.
 */
public interface AppointRepository extends JpaRepository<Appoint, Long>{
    /**
     * Извлекает список сущностей Запись, удовлетворяющих указанному ключевому слову.
     *
     * @param keyword
     * @return список сущностей запись
     */
    @Query("SELECT a FROM Appoint a WHERE CONCAT(a.id, ' ', a.doctor, ' ', a.patient, ' ', a.dateTime) LIKE %?1%")
    List<Appoint> search(String keyword);
    /**
     * Извлекает сущность Запись по условию.
     *
     * @param patient
     * @return список сущностей запись
     */
    @Query("select a from Appoint a where a.patient = ?1")
    List<Appoint> searchByPatient(Patient patient);
}
