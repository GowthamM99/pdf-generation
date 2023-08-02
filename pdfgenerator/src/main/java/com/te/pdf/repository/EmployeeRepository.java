package com.te.pdf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.pdf.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

}
