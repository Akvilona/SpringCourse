/**
 * Создал Андрей Антонов 15.08.2022 15:52
 **/
package ru.alishev.springcourse.FirstSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OTHER')")
    public void doAdminStuff(){
        System.out.println("Only Admin here");
    }
}
