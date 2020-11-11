package com.example.demo.jpa;

import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class StudentRepository {

    @Autowired
    EntityManager em;

    public void insert(Student student) {
        em.persist(student);
    }

    public List<Student> findAll() {
        return em.createQuery("SELECT s From Student s", Student.class).getResultList();
    }
}
