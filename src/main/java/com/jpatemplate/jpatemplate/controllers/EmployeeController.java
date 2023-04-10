package com.jpatemplate.jpatemplate.controllers;

import com.jpatemplate.jpatemplate.entities.Employee;
import com.jpatemplate.jpatemplate.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Component
@RequestMapping("/api/employee/")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    //@Autowired
    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.service = employeeService;
    }

    @RequestMapping(path = "/all/employees", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Employee>> findAllEmployees(){
        ResponseEntity<List<Employee>> employees = service.findAllEmployees();
        return employees;
    }

    @RequestMapping(path = "/all/employees/jdbctemplate", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Employee>> findAllEmployeesJDBC(){
        ResponseEntity<List<Employee>> employees = service.findAllEmployeesWithJdbcTemplate();
        return employees;
    }

    @RequestMapping(path = "/update/employees", method = RequestMethod.PUT)
    public void updateEmployeesJDBC(@RequestBody Employee employee, @RequestParam("id") int id){
         service.updateEmployee(employee, id);
    }

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    public void getEmployeesJDBC(@RequestParam("id") int id){
        service.getEmployeeByIdWithJdbcTemplate( id);
    }
}
