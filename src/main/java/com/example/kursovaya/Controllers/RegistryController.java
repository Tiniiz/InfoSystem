package com.example.kursovaya.Controllers;

import com.example.kursovaya.Repositories.PatientRepository;
import com.example.kursovaya.Services.PatientService;
import com.example.kursovaya.Services.UserService;
import com.example.kursovaya.Tables.Patient;
import com.example.kursovaya.Tables.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * Контроллер для обрабатывания регистрации новых пользователей (пациентов).
 *
 * @author Валентина Изотова
 */
@Controller
public class RegistryController {
    /**
     * Экземпляр сервися для работы с пользователями.
     * Ссылка на репозиторий {@link UserService}
     */
    @Autowired
    private UserService userService;
    /**
     * Экземпляр репозитория для работы с пациентами.
     * Ссылка на репозиторий {@link PatientRepository}
     */
    @Autowired
    private PatientService patientService;
    /**
     * Отображает вид регистрационной формы.
     *
     * @param model модель, которая будет использоваться представлением
     * @return имя представления
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }
    /**
     * Обрабатывает отправку регистрационной формы.
     *
     * @param userForm данные пользователя из формы
     * @param bindingResult результат проверки пользовательских данных
     * @param model модель, которая будет использоваться представлением
     * @return имя представления или перенаправление на форму создания пациента
     */
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/add_user";
    }
    /**
     * Отображает вид формы создания пациента.
     *
     * @param model модель, которая будет использоваться представлением
     * @return имя представления
     */
    @GetMapping("/add_user")
    public String NewPatient(Model model) {
        Patient patient = new Patient();
        model.addAttribute("patient", patient);
        return "new_user";
    }
    /**
     * Обрабатывает отправку формы создания пациента.
     *
     * @param patient the patient data from the form
     * @return перенаправление на домашнюю страницу
     */
    @PostMapping("/add_user")
    public String AddPatient(@ModelAttribute("patient") Patient patient){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        patient.setPhoneNum(auth.getName());
        patientService.add(patient);
        return "redirect:/";
    }

}