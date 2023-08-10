package com.example.fullApp.controller;

import com.example.fullApp.exception.ResourceNotFoundException;
import com.example.fullApp.model.Employee;
import com.example.fullApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository empRepo;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return empRepo.findAll();
    }


    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return empRepo.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = empRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Employee with id: %s doesnt exist "+id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody  Employee employeeDetails){
        Employee employee = empRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Employee with id: %s doesnt exist "+id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = empRepo.save(employee);

        return ResponseEntity.ok(updatedEmployee);

    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean> >deleteEmployee(@PathVariable Long id){
        Employee employee = empRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException("Employee with id: %s doesnt exist "+id));

        empRepo.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);

    }




}
