package com.tmgreyhat.lessons.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.tmgreyhat.lessons.Models.Employee;
import com.tmgreyhat.lessons.Repos.EmployeeRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employees")
class EmployeeController {

    private final EmployeeRepo repository;

    EmployeeController(EmployeeRepo repository) {
        this.repository = repository;
    }

    // Aggregate root

   /* @GetMapping
    List<Employee> all() {
        return repository.findAll();
    }*/

   @GetMapping
    CollectionModel<EntityModel<Employee>> all (){

       List<EntityModel<Employee>>  employees = repository.findAll()
               .stream()
               .map(employee ->  EntityModel.of(employee,

                       linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                       linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
                       ))
               .collect(Collectors.toList());

       return  CollectionModel.of(employees,
               linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
   }

    @PostMapping
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item

    @GetMapping(value = "/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {

        Employee employee = repository.findById(id) //
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
    }

    @PutMapping(value = "{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping(value = "/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}