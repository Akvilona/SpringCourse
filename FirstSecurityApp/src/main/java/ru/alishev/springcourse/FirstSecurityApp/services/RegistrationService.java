/**
 * Создал Андрей Антонов 12.08.2022 16:04
 **/
package ru.alishev.springcourse.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;
import ru.alishev.springcourse.FirstSecurityApp.repositories.PeopleRepository;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        /** получаем пароль с формы и получаем его
         * шифрованный код и обновляем пароль у пользователя */
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        /** сохраняем пользователя и пароль */
        peopleRepository.save(person);
    }
}
