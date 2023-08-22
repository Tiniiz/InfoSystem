package com.example.kursovaya.Services;

import com.example.kursovaya.Repositories.DoctorRepository;
import com.example.kursovaya.Tables.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Сервис для работы с врачами.
 *
 * @author Валентина Изотова
 */
@Service
public class DoctorService {
    /**
     * Экземпляр репозитория для работы с врачами.
     * Ссылка на репозиторий {@link DoctorRepository}
     */
    @Autowired
    private DoctorRepository rep;
    /**
     * Объект класса Random для генерации рандомных значений.
     */
    private final Random rnd = new Random();
    /**
     * Получить список всех врачей, соответствующих указанному ключевому слову.
     * @param keyword ключевое слово
     * @return список врачей по ключевому слову
     */
    public List<Doctor> ListAll(String keyword) {

        if (keyword != null) {
            return rep.search(keyword);
        }
        return rep.findAll();
    }
    /**
     * Заполнить таблицу Врачи 30 рандомными значениями на основе имеющихся списков.
     */
    public void fill(){

        String[] names = {"Александрова Анастасия Алексеевна",
                "Арустамян Даниэль Петрович",
                "Бажмин Андрей Иванович",
                "Байчурин Амир Максимович",
                "Болейнингер Иван Глебович",
                "Варданян Вардан Варданович",
                "Великуров Валентин Дмитриевич",
                "Герцен Виктория Антоеовна",
                "Герцен Оксана Георгиевна",
                "Гусев Андрей Владимирович",
                "Мелконян Роберт Аранович",
                "Дубинин Артем Геннадивьевич",
                "Ежов Тимофей Андреевич",
                "Кисина Анастасия Алексеевна",
                "Комаров Тимофей Владиславович",
                "Курочкина Алиса Дмитриевна",
                "Лось Александр Евгениевич",
                "Луцкий Никита Сергеевич",
                "Музыченко Дмитрий Сергеевич",
                "Панов Павел Андреевич",
                "Попов Константин Владимирович",
                "Хатьянов Родион Алексеевич",
                "Слабинский Василий Дмитриевич",
                "Сурин Константин Евгениевич",
                "Шибанов Михаил Владиславович",
                "Чащин Марк Михайлович",
                "Даниелян Арсен Амирович",
                "Костромаров Андрей Денисович",
                "Вольницкая Анна МАксимовна",
                "Каранян Ержагн Удмирсович"};

        String[] adresses = {  "ул. Юлиана Семенова, д. 2",
                "Москва, ул. Первомайская, д. 113",
                "ул. 16-я Парковая, д. 8, стр. 1",
                "Щелковское шоссе, д. 88А",
                "ул. Недорубова, д. 2",
                "Кавказский б-р, д. 45, стр. 1",
                "ул. Ленинская Слобода, д. 5, кор. 1",
                "Варшавское шоссе, д. 53, кор. 3",
                "ул. Азовская, д. 20, кор. 1",
                "Каширский проезд, д. 1/1",
                "ул. Ленинская Слобода, д. 5, кор. 1",
                "пр-кт Севастопольский, д. 24А, стр. 1",
                "ул. Уржумская, д. 4А",
        };

        String[] qualifs = {"Акушер",
"Аллерголог",
"Венеролог",
"Вирусолог",
"Гастроэнтеролог",
"Гинеколог",
"Главврач (Главный врач)",
"Дерматолог",
"Диетолог",
"Иммунолог",
"Инфекционист",
"Кардиолог",
"Косметолог",
"Невролог",
"Офтальмолог",
"Онколог",
"Ортопед",
"Отоларинголог",
"Проктолог",
"Психиатр",
"Ревматолог",
"Рентгенолог",
"Стоматолог",
"Терапевт",
"Травматолог",
"УЗИ-специалист",
"Уролог",
"Физиотерапевт",
"Хирург",
"Эндокринолог",};

        List<Doctor> doctors = new ArrayList<>();

        for (int i = 0; i < 30; i++){
            Doctor doctor = new Doctor();

            doctor.setName(names[i]);
            doctor.setBirthday("%d-%02d-%02d".formatted(rnd.nextInt(1960, 2003),
                    rnd.nextInt(1, 32), rnd.nextInt(1, 32)));
            doctor.setQualif(qualifs[i]);
            doctor.setPassport("45%d %d".formatted(rnd.nextInt(10, 18),
                    rnd.nextInt(100000, 999999)));
            doctor.setAddress(adresses[rnd.nextInt(0, adresses.length)]);

            doctors.add(doctor);
        }
        rep.saveAll(doctors);
    }
    /**
     * Сохранить экземпляр класса Врач в базу данных.
     *
     * @param doctor
     */
    public void add(Doctor doctor) {
        rep.save(doctor);
    }
    /**
     * Удалить экземпляр класса Врач из базы данных по его идентификатору.
     *
     * @param id
     */
    public void del(Long id) {
        rep.deleteById(id);
    }
    /**
     * Получить экземпляр класса Врач из базы данных по его идентификатору.
     *
     * @param id
     * @return врач, удолветворяющий условию
     */
    public Doctor get(Long id) {
        return rep.findById(id).get();
    }
    /**
     * Получить экземпляр класса Врач из базы данных по его имени.
     *
     * @param qualif
     * @return врач, удолветворяющий условию
     */
    public Doctor checkDoc(String qualif){
        return rep.doctor(qualif);
    }
    /**
     * Удалить данные из таблицы Врачи.
     */
    public void truncate() {
        rep.deleteAll();
    }
}
