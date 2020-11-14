package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log
@Entity
public class Course {
    @Getter @Setter @Id @GeneratedValue
    private int id;

    @Getter @Setter @Column(length = 1024)
    private String name;

    public Course(String name) {
        this.name = name;
    }

    @Getter
    @ManyToMany(mappedBy="courses")
    Set<Student> students = new HashSet<>();    // do not use List!!!

    public void addStudent(Student s) {
        this.students.add(s);
    }
}
