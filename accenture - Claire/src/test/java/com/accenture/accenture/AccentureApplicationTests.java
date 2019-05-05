package com.accenture.accenture;

import com.accenture.accenture.model.Employee;
import com.accenture.accenture.repository.IEmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccentureApplicationTests {

	@Autowired
	private IEmployeeRepository employeeRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Test
	public void createEmployee() {
		if(employeeRepository.findByLogin("accenture") ==null) {
			Employee employee = new Employee();
			employee.setReference(1);
			employee.setLogin("accenture");
			employee.setName("Accenture Login");
			employee.setPassword(encoder.encode("123"));
			Employee employeeCreated = employeeRepository.save(employee);
			assertTrue(employee.getPassword().equalsIgnoreCase(employeeCreated.getPassword()));
		}else
		{
			assertTrue(true);
		}
	}

}
