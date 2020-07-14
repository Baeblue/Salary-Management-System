package com.salary.repository;

import com.salary.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    List<Salary> findAll();
}
