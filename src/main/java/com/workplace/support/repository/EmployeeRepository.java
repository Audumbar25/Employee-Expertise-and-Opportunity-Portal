package com.workplace.support.repository;

import com.workplace.support.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public boolean existsByEmpEmail(String email);

    public Optional<Employee> findByEmpEmail(String email);

//    public Optional<Employee> findByEmpId(Long empId);
}
