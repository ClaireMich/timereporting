package com.accenture.accenture.repository;

import com.accenture.accenture.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByLogin(String login);
}
