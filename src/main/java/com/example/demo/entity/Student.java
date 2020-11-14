package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private int age;

    protected Student() {}

    public Student(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ManyToMany
    Set<Course> courses = new HashSet<>();  // DO NOT USE List!!!

    public void addCourse(Course c) {
        this.courses.add(c);
    }

    @Override
    public String toString() {
        return String.format("id=%s, name=%s", this.id, this.name);
    }
}
