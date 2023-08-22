package com.example.kursovaya.Services;

import com.example.kursovaya.Repositories.PatientRepository;
import com.example.kursovaya.Tables.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Сервис для работы с пациентами.
 *
 * @author Валентина Изотова
 */
@Service
public class PatientService {
    /**
     * Экземпляр репозитория для работы с пациентами.
     * Ссылка на репозиторий {@link PatientRepository}
     */
    @Autowired
    private PatientRepository rep;
    /**
     * Объект класса Random для генерации рандомных значений.
     */
    private final Random rnd = new Random();
    /**
     * Получить список всех пациентов, соответствующих указанному ключевому слову.
     * @param keyword ключевое слово
     * @return список пациентов по ключевому слову
     */
    public List<Patient> ListAll(String keyword) {

        if (keyword != null) {
            return rep.search(keyword);
        }
        return rep.findAll();
    }
    /**
     * Заполнить таблицу Пациенты 30 рандомными значениями на основе имеющихся списков.
     */
    public void fill(){

        String[] names = {"Анпилов Кирилл Михайлович",
                "Архипов Илья Константинович",
                "Балашкин Андрей Михайлович",
                "Богута Олег Владиславович",
                "Губанов Иван Владимирович",
                "Еремеев Денис Игоревич",
                "Зайцева Екатерина Алексеевна",
                "Зиновьева Елизавета Николаевна",
                "Золотов Дмитрий Витальевич",
                "Изотова Валентина Дмитриевна",
                "Кандаурова Дарья Дмитриевна",
                "Кондрашов Дмитрий Михайлович",
                "Коробков Владислав Денисович",
                "Кузнецова Маргарита Олеговна",
                "Кусербаев Карим Ильгамович",
                "Макин Владислав Николаевич",
                "Марусенко Даниил Евгеньевич",
                "Назарова Анастасия Алексеевна",
                "Оганесян Мисак Гагикович",
                "Петриков Артём Евгеньевич",
                "Радченко Глеб Александрович",
                "Скуратов Артем Геннадьевич",
                "Суслов Андрей Вадимович",
                "Фархутдинов Айдар Зинфирович",
                "Цурин Тимофей Дмитриевич",
                "Чудаева Светлана Алексеевна",
                "Колоскова Валерия Максимовна",
                "Антонова Ольга Петровна",
                "Пантелеев Вячеслав Андреевич",
                "Нечаева Нина Юрьевна"};

        String[] adresses = {  "Каширское шоссе, д.94, корп.3",
                "Малая Бронная ул., д. 15",
                "Шипиловская ул, д. 18",
                "Спасопесковский переулок, д. 6, стр. 7",
                "ул. Плющиха, д. 16, стр 3",
                "Кропоткинский пер., д. 10",
                "Ружейный пер., д. 8",
                "1-я Фрунзенская ул., д. 6А",
                "ул. Пречистенка, д. 12/2, стр. 8",
                "Большая Пироговская ул., д. 53",
                "Каширское шоссе, д.94, корп.1",
                "Шипиловская ул, д.22",
                "Трифоновская ул., д. 34",
        };

        List<Patient> patients = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            Patient patient = new Patient();
            patient.setName(names[i]);
            patient.setBirthday("%d-%02d-%02d".formatted(rnd.nextInt(1960, 2003),
                    rnd.nextInt(1,12), rnd.nextInt(1, 31)));
            patient.setMedPolNum("%d".formatted(rnd.nextInt(100000000, 999999999)));
            patient.setPassport("45%d %d".formatted(rnd.nextInt(10, 18),
                    rnd.nextInt(100000, 999999)));
            patient.setAddress(adresses[rnd.nextInt(0, adresses.length)]);
            patient.setPhoneNum("89%02d%d".formatted(rnd.nextInt(1, 99),
                    rnd.nextInt(1000000, 9999999)));
            patients.add(patient);
        }
        rep.saveAll(patients);
    }
    /**
     * Сохранить экземпляр класса Пациент в базу данных.
     *
     * @param patient
     */
    public void add(Patient patient) {
        rep.save(patient);
    }
    /**
     * Удалить экземпляр класса Пациент из базы данных по его идентификатору.
     *
     * @param id
     */
    public void del(Long id) {
        rep.deleteById(id);
    }
    /**
     * Получить экземпляр класса Пациент из базы данных по его идентификатору.
     *
     * @param id
     * @return пациент, удолветворяющий условию
     */
    public Patient get(Long id) {
        return rep.findById(id).get();
    }
    /**
     * Получить экземпляр класса Пациент из базы данных по его имени.
     *
     * @param name
     * @return пациент, удолветворяющий условию
     */
    public Patient checkPat(String name){
        return rep.patient(name);
    }
    /**
     * Удалить данные из таблицы Пациенты.
     */
    public void truncate() {
        rep.deleteAll();
    }
}
