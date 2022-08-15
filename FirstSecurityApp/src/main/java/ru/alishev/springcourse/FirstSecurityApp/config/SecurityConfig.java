/**
 * Создал Андрей Антонов 08.08.2022 17:44
 **/
package ru.alishev.springcourse.FirstSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailService;

@EnableWebSecurity
public class  SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailService personDetailService;

    @Autowired
    public SecurityConfig(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    protected void configure (HttpSecurity http) throws Exception  {
        // конфигурируем спринг секюрити
        http.csrf().disable() // Отключаем защиту от меж сайтовой подделки запросов
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/error" ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }

    /** метод настраивает аутентификацию */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /** для того чтобы использовать шифрование добавляем .passwordEncoder() */
        auth.userDetailsService(personDetailService).passwordEncoder(getPasswordEncoder());
    }

    /** указываем что пароль ни как не шифруется */
    @Bean
    public PasswordEncoder getPasswordEncoder () {

        return new BCryptPasswordEncoder();
    }

}
