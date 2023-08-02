package com.te.pdf.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.te.pdf.dto.EmployeeDTO;
import com.te.pdf.entity.Employee;
import com.te.pdf.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private final EmployeeRepository employeeRepository;
	
	@Override
	public String saveEmployee(EmployeeDTO employeeDTO) {
		Employee employee=new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		employeeRepository.save(employee);
		return employee.getEmployeeId();
	}

}
