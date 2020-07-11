package com.tmgreyhat.lessons;

import com.tmgreyhat.lessons.Models.Employee;
import com.tmgreyhat.lessons.Repos.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


public class Preloader {

    private  static final Logger log = LoggerFactory.getLogger(Preloader.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepo repo){

        return args -> {

            log.info("Preloading "+ repo.save(new Employee("Tapiwa Nashe", "Runner")));
            log.info("Preloading "+ repo.save(new Employee("Takudzwa Nashe", "Jumber")));
        };


    }
}
