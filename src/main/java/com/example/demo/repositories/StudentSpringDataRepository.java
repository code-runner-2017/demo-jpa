package com.example.demo.repositories;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSpringDataRepository extends JpaRepository<Student, Long> {
    List<Course> findByNameAndId(String name, Long id);
    List<Course> findByName(String name);
    List<Course> findByNameOrderByIdDesc(String name);
    int countByName(String name);

}
