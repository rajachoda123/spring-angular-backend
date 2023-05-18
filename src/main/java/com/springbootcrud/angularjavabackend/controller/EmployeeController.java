package com.springbootcrud.angularjavabackend.controller;

import com.springbootcrud.angularjavabackend.entity.Employee;
import com.springbootcrud.angularjavabackend.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private  EmployeeRepository employeeRepository;

    //Get all employess
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    // save employee
    @PostMapping("/employees")
    public Employee save(@RequestBody Employee employee) {
       return employeeRepository.save(employee);
    }

    // get employee
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    // update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee entityEmployee = employeeRepository.findById(employee.getId()).orElseThrow(() -> new EntityNotFoundException(String.format("Not found %s", employee.getId())));
        entityEmployee.setFirstName(employee.getFirstName());
        entityEmployee.setLastName(employee.getLastName());
        entityEmployee.setEmailId(employee.getEmailId());
        entityEmployee = employeeRepository.save(employee);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(entityEmployee);
    }

    // delete employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not exist with id " + id));
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        employeeRepository.delete(employee);
        return ResponseEntity.ok(response);
    }

}
