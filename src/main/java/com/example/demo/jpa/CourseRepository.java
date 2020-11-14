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
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.UnaryOperator;

@Repository
@Transactional
public class CourseRepository {

    @Autowired
    EntityManager em;

    public Course save(Course course) {
        em.persist(course);
        return course;
    }

    public List<Course> findAll() {
        return em.createQuery("SELECT c From Course c", Course.class).getResultList();
    }

    public void forEachStudent(Course course, UnaryOperator<Student> lambda) {
        Session session = em.unwrap(Session.class);
        Query<Student> query = em.unwrap(Session.class).createQuery("SELECT s From Student s INNER JOIN s.courses c WHERE c.id = :id", Student.class);
        query.setParameter("id", course.getId());
        ScrollableResults results = query.setFetchSize(100).setReadOnly(true).setCacheable(false).scroll(ScrollMode.FORWARD_ONLY);

        while (results.next()) {
            Student student = (Student) results.get()[0];
            session.detach(student);
            lambda.apply(student);
        }
    }
}
