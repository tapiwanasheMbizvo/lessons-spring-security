package com.tmgreyhat.lessons.Repos;

import com.tmgreyhat.lessons.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
