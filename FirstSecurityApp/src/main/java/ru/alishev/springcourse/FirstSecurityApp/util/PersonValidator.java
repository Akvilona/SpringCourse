/**
 * Создал Андрей Антонов 12.08.2022 15:38
 **/
package ru.alishev.springcourse.FirstSecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailService;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailService personDetailService;

    @Autowired
    public PersonValidator(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

        try{
            personDetailService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored){
            return; //  все Ok пользователь с таким именем не найден
        }
        /** если код пришел сюда, значит такой человек был найден */
        errors.rejectValue("username", "", "Человек с таким именем ужу существует.");
    }
}
