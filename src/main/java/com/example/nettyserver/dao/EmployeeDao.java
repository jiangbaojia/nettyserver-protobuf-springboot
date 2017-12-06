package com.example.nettyserver.dao;

import com.example.nettyserver.entity.Employee;

import java.util.List;

public interface EmployeeDao {

	void saveEmployee(Employee employee);
	
	List<Employee> findAllEmployees();
	
	void deleteEmployeeBySsn(String ssn);
}
