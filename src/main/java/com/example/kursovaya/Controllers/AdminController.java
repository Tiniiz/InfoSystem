package com.example.kursovaya.Controllers;

import com.example.kursovaya.Repositories.AppointRepository;
import com.example.kursovaya.Repositories.DoctorRepository;
import com.example.kursovaya.Repositories.PatientRepository;
import com.example.kursovaya.Services.AppointService;
import com.example.kursovaya.Services.DoctorService;
import com.example.kursovaya.Services.PatientService;
import com.example.kursovaya.Tables.Appoint;
import com.example.kursovaya.Tables.Doctor;
import com.example.kursovaya.Tables.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Контроллер, который обрабатывает различные запросы, связанные с записями, врачами и пациентами.
 *
 * @author Валентина Изотова
 */
@Controller
public class AdminController {

    /**
     * Экземпляр сервиса для работы с врачами.
     * Ссылка на репозиторий {@link DoctorService}
     */
    @Autowired
    private DoctorService doctorService;

    /**
     * Экземпляр сервиса для работы с пациентами.
     * Ссылка на репозиторий {@link PatientRepository}
     */
    @Autowired
    private PatientService patientService;

    /**
     * Экземпляр сервиса для работы с записями.
     * Ссылка на репозиторий {@link AppointService}
     */
    @Autowired
    private AppointService appointService;

    /**
     * Экземпляр репозитория для работы с пациентами.
     * Ссылка на репозиторий {@link PatientRepository}
     */
    @Autowired
    private PatientRepository rep;

    /**
     * Экземпляр репозитория для работы с записями.
     * Ссылка на репозиторий {@link AppointRepository}
     */
    @Autowired
    private AppointRepository repa;

    /**
     * Экземпляр репозитория для работы с врачами.
     * Ссылка на репозиторий {@link DoctorRepository}
     */
    @Autowired
    private DoctorRepository repd;


    /**
     * Отображает главную страницу приложения.
     *
     * @param model модель, которая будет использоваться для передачи данных на страницу
     * @param keyword ключевое слово для поиска записей
     * @param authentication объект аутентификации пользователя
     * @return  строку, содержащую имя представления для отображения главной страницы
     */
    @RequestMapping("/")
    public String HomePage(Model model, @Param("keyword") String keyword, Authentication authentication) {
        if (authentication != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (AuthorityUtils.authorityListToSet(auth.getAuthorities()).contains("ROLE_ADMIN")) {
                List<Appoint> appoint = appointService.ListAll(keyword);
                model.addAttribute("appoint", appoint);

            } else {
                Patient patient = rep.searchOne(auth.getName());
                if (patient != null) {
                    List<Appoint> appoint = repa.searchByPatient(patient);
                    model.addAttribute("appoint", appoint);
                }
            }
            model.addAttribute("keyword", keyword);
        }

        return "index";
    }

    /**
     * Обработчик GET-запроса на создание нового приема.
     * Отображает форму создания нового приема.
     *
     * @param model объект для хранения данных модели
     * @return строку, представляющую имя представления для отображения формы создания нового приема
     */
    @RequestMapping("/appoint/new")
    public String newAppoint(Model model) {

        Appoint appoint = new Appoint();
        List<Doctor> doctors = repd.findAll();
        List<Patient> patients = rep.findAll();
        model.addAttribute("appoint", appoint);
        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patients);

        return "create_appoint";
    }

    /**
     * Обрабатывает POST-запрос для сохранения записи в базе данных.
     *
     * @param appoint Объект назначения, который необходимо сохранить.
     * @return Перенаправление на домашнюю страницу
     */
    @RequestMapping(value = "/appoint/save", method = RequestMethod.POST)
    public String saveNewAppoint(@ModelAttribute("appoint") Appoint appoint, @Param("date") String date){
        appoint.setDateTime(date);
        appointService.add(appoint);
        return "redirect:/";
    }

    /**
     * Контроллер для редактирования записи на прием к врачу
     *
     * @param id идентификатор записи на прием
     * @param model объект модели, который используется для передачи данных в представление
     * @return имя представления, которое будет использоваться для отображения страницы редактирования записи на прием
     */
    @RequestMapping("/appoint/edit/{id}")
    public String editAppoint(@PathVariable Long id, Model model) {
        Appoint appoint = appointService.get(id);
        List<Doctor> doctors = repd.findAll();
        List<Patient> patients = rep.findAll();
        model.addAttribute("appoint", appoint);
        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patients);
        return "appoint_edit";
    }

    /**
     * Обрабатывает HTTP GET-запросы на удаление записи на прием с указанным ID.
     *
     * @param id идентификатор удаляемой записи.
     * @return перенаправление на домашнюю страницу после удаления записи на прием.
     */
    @RequestMapping("/appoint/delete/{id}")
    public String deleteAppoint(@PathVariable(name = "id") Long id) {
        appointService.del(id);
        return "redirect:/";
    }

    /**
     * Обрабатывает HTTP GET-запросы для заполнения данных о записи.
     * Заполняет данные о записях данными выборки.
     *
     * @return перенаправление на домашнюю страницу после заполнения таблицы записей.
     */
    @RequestMapping("/appoint/fill/")
    public String fillAppoint() {
        appointService.fill();
        return "redirect:/";
    }

    /**
     * Метод контроллера для обработки HTTP GET запросов к конечной точке "/appoint/truncate/".
     * Удаляет все значения в таблице Записи в базе данных.
     *
     * @return перенаправление на домашнюю страницу после удаления записей.
     */
    @RequestMapping("/appoint/truncate/")
    public String truncateAppoint() {
        appointService.truncate();
        return "redirect:/";
    }

    /**
     * Отображает страницу приложения с данными авторизованного пользователя.
     *
     * @param model модель, которая будет использоваться для передачи данных на страницу.
     * @return  строку, содержащую имя представления для отображения страницы.
     */
    @RequestMapping( "/user")
    public String currentUserName(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = rep.searchOne(auth.getName());

        model.addAttribute("patient", patient);
        return "user";
    }

    /**
     * Обрабатывает POST-запрос для сохранения пользователем новой записи в базе данных.
     *
     * @param model Объект модели, который будет использоваться для добавления атрибутов.
     * @return Вид, который будет отображаться после сохранения записи.
     */
    @RequestMapping("/appoint/user/new")
    public String addAppoint(Model model){
        Appoint appoint = new Appoint();

        model.addAttribute("appoint", appoint);
        model.addAttribute("doctor", repd.findAll());

        return "create_appoint";
    }

    /**
     * Обрабатывает POST-запрос для сохранения новой встречи в базе данных.
     *
     * @param appoint Объект записи, который необходимо сохранить.
     * @param date дата записи.
     * @param time время записи.
     * @return перенаправление на домашнюю страницу после добавления записи.
     */
    @RequestMapping(value = "/appoint/user/save", method = RequestMethod.POST)
    public String saveAppoint(@ModelAttribute("appoint") Appoint appoint, @ModelAttribute("doctors") List<Doctor> doctors,
                              @Param("date") String date, @Param("time") String time){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = rep.searchOne(auth.getName());

        appoint.setPatient(patient);
        appoint.setDateTime(date + ' ' + time);

        appointService.add(appoint);

        return "redirect:/";
    }

    /**
     * Контроллер для редактирования профиля пациента
     *
     * @param id идентификатор пациента
     * @param model объект модели, который используется для передачи данных в представление
     * @return имя представления, которое будет использоваться для отображения страницы редактирования профиля пациента
     */
    @RequestMapping("/patient/user/edit/{id}")
    public String editAppointPatient(@PathVariable Long id, Model model) {
        Patient patient = patientService.get(id);
        model.addAttribute("patient", patient);
        return "patient_edit";
    }

    /**
     * Отображает страницу приложения с данными врачей.
     *
     * @param model модель, которая будет использоваться для передачи данных на страницу.
     * @param keyword ключевое слово для поиска врачей
     * @return  строку, содержащую имя представления для отображения страницы.
     */
    @RequestMapping("/doctor")
    public String Doctors(Model model, @Param("keyword") String keyword) {

        List<Doctor> doctor = doctorService.ListAll(keyword);
        model.addAttribute("doctor", doctor);
        model.addAttribute("keyword", keyword);

        return "doctors";
    }

    /**
     * Обрабатывает POST-запрос для сохранения нового врача в базе данных.
     *
     * @param model Объект модели, который будет использоваться для добавления атрибутов.
     * @return Вид, который будет отображаться после сохранения врача.
     */
    @RequestMapping("/doctor/new")
    public String newDoctor(Model model) {
        Doctor doctor = new Doctor();
        model.addAttribute("doctor", doctor);
        return "create_doctor";
    }

    /**
     * Обрабатывает POST-запрос для сохранения нового врача в базе данных.
     *
     * @param doctor Объект назначения, который необходимо сохранить.
     * @return перенаправление на страницу врачей после добавления врача.
     */
    @RequestMapping(value = "/doctor/save", method = RequestMethod.POST)
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.add(doctor);
        return "redirect:/doctor";
    }

    /**
     * Метод контроллера для отображения формы редактирования данных о враче для заданного идентификатора врача.
     *
     * @param id идентификатор врача для редактирования.
     * @return объект ModelAndView, представляющий представление "doctor_edit" с объектом назначения в качестве атрибута модели.
     */
    @RequestMapping("/doctor/edit/{id}")
    public ModelAndView editDoctor(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("doctor_edit");
        Doctor doctor = doctorService.get(id);
        mav.addObject("doctor", doctor);
        return mav;
    }

    /**
     * Обрабатывает HTTP GET-запросы на удаление врача с указанным ID.
     *
     * @param id идентификатор удаляемого врача.
     * @return перенаправление на страницу врачей после удаления врача.
     */
    @RequestMapping("/doctor/delete/{id}")
    public String deleteDoctor(@PathVariable(name = "id") Long id) {
        doctorService.del(id);
        return "doctors";
    }

    /**
     * Обрабатывает HTTP GET-запросы для заполнения данных о врачах.
     * Заполняет данные о врачах данными выборки.
     *
     * @return перенаправление на домашнюю страницу после удаления врачей.
     */
    @RequestMapping("/doctor/fill/")
    public String fillDoctor() {
        doctorService.fill();
        return "redirect:/";
    }

    /**
     * Метод контроллера для обработки HTTP GET запросов к конечной точке "/doctor/truncate/".
     * Удаляет все значения в таблице Врачи в базе данных.
     *
     * @return перенаправление на домашнюю страницу после удаления врачей.
     */
    @RequestMapping("/doctor/truncate/")
    public String truncateDoctor() {
        doctorService.truncate();
        return "redirect:/";
    }

    /**
     * Отображает страницу приложения с данными пациентов.
     *
     * @param model модель, которая будет использоваться для передачи данных на страницу.
     * @param keyword ключевое слово для поиска пациентов
     * @return  строку, содержащую имя представления для отображения страницы.
     */
    @RequestMapping("/patient")
    public String Patients(Model model, @Param("keyword") String keyword) {
        List<Patient> patient = patientService.ListAll(keyword);

        model.addAttribute("patient", patient);
        model.addAttribute("keyword", keyword);

        return "patients";
    }

    /**
     * Обрабатывает POST-запрос для сохранения нового пациента в базе данных.
     *
     * @param model Объект модели, который будет использоваться для добавления атрибутов.
     * @return Вид, который будет отображаться после сохранения пациента.
     */
    @RequestMapping("/patient/new")
    public String newPatient(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "create_patient";
    }

    /**
     * Контроллер для сохранения профиля пациента
     *
     * @param patient объект класса Patient, который содержит данные о пациенте, переданные из формы
     * @return имя представления, которое будет использоваться для перенаправления после сохранения профиля пациента
     */
    @RequestMapping(value = "/patient/save", method = RequestMethod.POST)
    public String savePatient(@ModelAttribute("patient") Patient patient) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (AuthorityUtils.authorityListToSet(auth.getAuthorities()).contains("ROLE_ADMIN")) {
            patientService.add(patient);
            return "redirect:/patient";
        } else {
            patient.setPhoneNum(auth.getName());
            patientService.add(patient);
            return "redirect:/user";
        }
    }

    /**
     * Метод контроллера для отображения формы редактирования данных о пациенте для заданного идентификатора пациента.
     *
     * @param id идентификатор пациента для редактирования.
     * @return объект ModelAndView, представляющий представление "patient_edit" с объектом назначения в качестве атрибута модели.
     */
    @RequestMapping("/patient/edit/{id}")
    public ModelAndView editPatient(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("patient_edit");
        Patient patient = patientService.get(id);
        mav.addObject("patient", patient);
        return mav;
    }

    /**
     * Обрабатывает HTTP GET-запросы на удаление пациента с указанным ID.
     *
     * @param id идентификатор пациента для удаления.
     * @return перенаправление на страницу пациентов после удаления данных.
     */
    @RequestMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable(name = "id") Long id) {
        patientService.del(id);
        return "redirect:/patient";
    }

    /**
     * Обрабатывает HTTP GET-запросы для заполнения данных о пациентах.
     * Заполняет данные о пациентах данными выборки.
     *
     * @return перенаправление на домашнюю страницу после удаления данных.
     */
    @RequestMapping("/patient/fill/")
    public String fillPatient() {
        patientService.fill();
        return "redirect:/";
    }

    /**
     * Метод контроллера для обработки HTTP GET запросов к конечной точке "/patient/truncate/".
     * Удаляет все значения в таблице Пациенты в базе данных.
     *
     * @return перенаправление на домашнюю страницу после удаления пациентов.
     */
    @RequestMapping("/patient/truncate/")
    public String truncatePatient() {
        patientService.truncate();
        return "redirect:/";
    }

    /**
     * Отображает страницу приложения с данными об авторе приложения.
     *
     * @return  строку, содержащую имя представления для отображения страницы.
     */
    @RequestMapping("/author")
    public String authorPage(){
        return "author";
    }

}