package com.example.kursovaya.Services;

import com.example.kursovaya.Repositories.AppointRepository;
import com.example.kursovaya.Repositories.DoctorRepository;
import com.example.kursovaya.Repositories.PatientRepository;
import com.example.kursovaya.Tables.Appoint;
import com.example.kursovaya.Tables.Doctor;
import com.example.kursovaya.Tables.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Сервис для работы с записями.
 *
 * @author Валентина Изотова
 */
@Service
public class AppointService {
    /**
     * Экземпляр репозитория для работы с записями.
     * Ссылка на репозиторий {@link AppointRepository}
     */
    @Autowired
    private AppointRepository rep;
    /**
     * Экземпляр репозитория для работы с врачами.
     * Ссылка на репозиторий {@link DoctorRepository}
     */
    @Autowired
    private DoctorRepository repd;
    /**
     * Экземпляр репозитория для работы с пациентами.
     * Ссылка на репозиторий {@link PatientRepository}
     */
    @Autowired
    private PatientRepository repp;
    /**
     * Объект класса Random для генерации рандомных значений.
     */
    private final Random rnd = new Random();
    /**
     * Получить список всех записей, соответствующих указанному ключевому слову.
     * @param keyword ключевое слово
     * @return список записей по ключевому слову
     */
    public List<Appoint> ListAll(String keyword) {
        if (keyword != null) {
            return rep.search(keyword);
        }
        return rep.findAll();
    }
    /**
     * Заполнить таблицу Записи 50 рандомными значениями.
     */
    public void fill(){

        List<Appoint> appoints = new ArrayList<>();
        List<Patient> patients = repp.searchPatient();
        List<Doctor> doctors = repd.searchDoctor();

        for (int i = 0; i < 50; i++){
            Appoint appoint = new Appoint();

            appoint.setPatient(patients.get(rnd.nextInt(0, 30)));
            appoint.setDoctor(doctors.get(rnd.nextInt(0, 30)));
            appoint.setDateTime("2023-04-%02d %d:%02d".formatted(rnd.nextInt(1, 31),
                    rnd.nextInt(10, 20), rnd.nextInt(0, 60)));

            appoints.add(appoint);
        }
        rep.saveAll(appoints);
    }
    /**
     * Сохранить экземпляр класса Запись в базу данных.
     *
     * @param appoint
     */
    public void add(Appoint appoint) {
        rep.save(appoint);
    }
    /**
     * Удалить экземпляр класса Запись из базы данных по его идентификатору.
     *
     * @param id
     */
    public void del(Long id) {
        rep.deleteById(id);
    }
    /**
     * Получить экземпляр класса Запись из базы данных по его идентификатору.
     *
     * @param id
     * @return пациент, удолветворяющий условию
     */
    public Appoint get(Long id) {
        return rep.findById(id).get();
    }
    /**
     * Удалить данные из таблицы Записи.
     */
    public void truncate() {
        rep.deleteAll();
    }
}
