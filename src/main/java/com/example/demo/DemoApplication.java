package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.jpa.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Student pietro = new Student("Pietro");
        Student pippo = new Student("Pippo");

        studentRepository.insert(pietro);
        studentRepository.insert(pippo);

        List<Student> all = studentRepository.findAll();

        System.out.println();
        System.out.println();
        System.out.println("**************** RESULTS:");
        System.out.println(all);
        System.out.println();
        System.out.println();
    }
}
