package com.workplace.support.repository;

import com.workplace.support.entity.Employee;
import com.workplace.support.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportunityRepository extends JpaRepository<Opportunity,Long> {
    List<Opportunity> findByCreatedBy(Employee employee);
}
