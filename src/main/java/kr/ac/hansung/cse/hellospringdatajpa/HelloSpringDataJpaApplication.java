package kr.ac.hansung.cse.hellospringdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "kr.ac.hansung.cse.hellospringdatajpa")
@EnableJpaRepositories(basePackages = "kr.ac.hansung.cse.hellospringdatajpa.repository")
public class HelloSpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringDataJpaApplication.class, args);
    }

}
