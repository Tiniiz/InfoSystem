package com.example.kursovaya.Services;

import com.example.kursovaya.Repositories.RoleRepository;
import com.example.kursovaya.Repositories.UserRepository;
import com.example.kursovaya.Tables.Role;
import com.example.kursovaya.Tables.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
/**
 * Сервис для работы с пользователями.
 *
 * @author Валентина Изотова
 */
@Service
public class UserService implements UserDetailsService {
    /**
     * Указывает на зависимость EntityManager в контейнере.
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * Экземпляр репозитория для работы с пользователями.
     * Ссылка на репозиторий {@link UserRepository}
     */
    @Autowired
    UserRepository userRepository;
    /**
     * Экземпляр репозитория для работы с ролями пользователей.
     * Ссылка на репозиторий {@link RoleRepository}
     */
    @Autowired
    RoleRepository roleRepository;
    /**
     * Экземпляр объекта для хеширования паролей пользователей.
     */
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * Метод для загрузки пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return пользователь с указанным именем пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
    /**
     * Метод для поиска пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return пользователь с указанным идентификатором или пустой объект User, если пользователь не найден
     */
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    /**
     * Метод для получения списка всех пользователей.
     *
     * @return список всех пользователей
     */
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    /**
     * Метод для сохранения нового пользователя.
     *
     * @param user новый пользователь для сохранения
     * @return true, если пользователь сохранен успешно, false, если пользователь с таким именем пользователя уже существует
     */
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    /**
     * Метод для удаления пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя, который нужно удалить
     * @return true, если пользователь удален успешно, false, если пользователь с указанным идентификатором не найден
     */
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    /**
     * Метод для получения списка пользователей с идентификаторами, большими заданного значения.
     *
     * @param idMin минимальное значение идентификатора
     * @return список пользователей с идентификаторами, большими заданного значения
     */
    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

}