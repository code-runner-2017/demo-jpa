package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy="courses")
    List<Student> students = new ArrayList<>();

    public void addStudent(Student s) {
        this.students.add(s);
        s.addCourse(this);
    }
}
