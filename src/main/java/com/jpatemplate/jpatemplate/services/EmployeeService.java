package com.jpatemplate.jpatemplate.services;

import com.jpatemplate.jpatemplate.Repository.EmployeeRepository;
import com.jpatemplate.jpatemplate.entities.Employee;
import com.speedment.jpastreamer.application.JPAStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    //
    // @Autowired
    private JPAStreamer jpaStreamer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeRepository repository;

    private RowMapper rowMapper = (rs, rowNum) -> {
        Employee emp = new Employee();
        emp.setEmployeeid(rs.getLong(1));
        emp.setLastname(rs.getString(2));
        emp.setFirstname(rs.getString(3));
        emp.setAge(rs.getInt(4));
        emp.setVilleid(rs.getLong(5));
        return emp;
    };

    public ResponseEntity<List<Employee>> findAllEmployees() {
        List<Employee> employees = repository.findAll();
        if (!employees.isEmpty()) {
            logger.info("the list of employees is not empty");
            ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
            return responseEntity;
        }
        logger.warn("the list of employees is empty!!!");
        return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
    }

    public void updateEmployee(Employee employee, int id) {
        int insert = jdbcTemplate.update(
                "update employee" +
                        "    set lastname = ? , firstname = ?, age = ?, villeid = ? " +
                        " where employeeid = ?", employee.getLastname(), employee.getFirstname(),
                employee.getAge(), employee.getVilleid(), id);

        if (insert == 1) {
            logger.info("the employee has been updated!");

        } else {
            logger.warn("the update went wrong!!!");

        }
    }

    public ResponseEntity<List<Employee>> findAllEmployeesWithJdbcTemplate() {
        List<Employee> employees = jdbcTemplate.query("select * from employee", (rs, rowNum) -> {
            Employee emp = new Employee();
            emp.setEmployeeid(rs.getLong(1));
            emp.setLastname(rs.getString(2));
            emp.setFirstname(rs.getString(3));
            emp.setAge(rs.getInt(4));
            emp.setVilleid(rs.getLong(5));
            return emp;
        });
        if (!employees.isEmpty()) {
            logger.info("the list of employees is not empty");
            ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
            return responseEntity;
        }
        logger.warn("the list of employees is empty!!!");
        return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Optional<Employee>>  getEmployeeByIdWithJdbcTemplate(int id) {
        String sql = "select * from employee where employeeid = ? ";
        Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
        if (employee!=null){
            return new ResponseEntity<>(Optional.of(employee), HttpStatus.OK);
        }
        logger.warn("the list of employees is empty!!!");
        return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Employee>> findAllEmployeesWithJpaSteamer() {
        List<Employee> employees = Arrays.asList();
        if (!employees.isEmpty()) {
            logger.info("the list of employees is not empty");
            ResponseEntity<List<Employee>> responseEntity = new ResponseEntity<>(employees, HttpStatus.OK);
            return responseEntity;
        }
        logger.warn("the list of employees is empty!!!");
        return new ResponseEntity<>(employees, HttpStatus.NOT_FOUND);
    }

}
