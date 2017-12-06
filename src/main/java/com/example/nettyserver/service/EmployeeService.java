package com.example.nettyserver.service;

import com.example.nettyserver.entity.Employee;

import java.util.List;

public interface EmployeeService {

	void saveEmployee(Employee employee);
	
	List<Employee> findAllEmployees(); 
	
	void deleteEmployeeBySsn(String ssn);
}
