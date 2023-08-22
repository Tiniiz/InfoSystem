package com.example.kursovaya.Repositories;

import com.example.kursovaya.Tables.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * @author Валентина Изотова
 *
 * Интерфейс репозитория для управления сущностями Врач.
 *
 * Этот интерфейс расширяет JpaRepository, предоставляя все операции CRUD для сущностей Врач.
 */
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    /**
     * Извлекает список сущностей Врач, удовлетворяющих указанному ключевому слову.
     *
     * @param keyword
     * @return список сущностей врач
     */
    @Query("SELECT d FROM Doctor d WHERE CONCAT(d.id, ' ', d.name, ' ', d.qualif) LIKE %?1%")
    List<Doctor> search(String keyword);
    /**
     * Извлекает сущность Врач по условию.
     *
     * @param qualif
     * @return сущность врач
     */
    @Query("select d from Doctor d where lower(d.qualif) = lower(?1)")
    Doctor doctor(String qualif);
    /**
     * Извлекает список всех сущностей представленных в таблице Врач.
     *
     * @return список сущностей врач
     */
    @Query("select d from Doctor d")
    List<Doctor> searchDoctor();

}
