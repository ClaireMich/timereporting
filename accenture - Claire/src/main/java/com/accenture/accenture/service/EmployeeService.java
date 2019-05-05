package com.accenture.accenture.service;

import com.accenture.accenture.model.Employee;
import com.accenture.accenture.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Employee employee= employeeRepository.findByLogin(login);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ADMIN"));

        UserDetails userDetails = new User(employee.getLogin(), employee.getPassword(), roles);

        return userDetails;
    }

    public int getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findByLogin(authentication.getName());
        return employee.getReference();
    }

    public String getCurrentUserLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void createEmployee() {
        if(employeeRepository.findByLogin("accenture") ==null) {
            Employee employee = new Employee();
            employee.setReference(1);
            employee.setLogin("accenture");
            employee.setName("Accenture Login");
            employee.setPassword("$2a$10$MutgxWVv0ho.KtwpQsJXoOCxlPenD/9MBAYwpMotzUGuMORvnwQNi");
            employeeRepository.save(employee);
        }
    }
}
