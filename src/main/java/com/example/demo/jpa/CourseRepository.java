package com.example.demo.jpa;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.UnaryOperator;

@Repository
@Transactional
public class CourseRepository {

    @Autowired
    EntityManager em;

    public void insert(Course course) {
        em.persist(course);
    }

    public List<Course> findAll() {
        return em.createQuery("SELECT c From Course c", Course.class).getResultList();
    }
}
