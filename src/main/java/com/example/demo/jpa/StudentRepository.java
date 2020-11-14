package com.example.demo.jpa;

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
public class StudentRepository {

    @Autowired
    EntityManager em;

    public void save(Student student) {
        em.persist(student);
    }

    public List<Student> findAll() {
        return em.createQuery("SELECT s From Student s", Student.class).getResultList();
    }

    public void createRandomStudents(int count) {
        em.createNativeQuery("delete from student").executeUpdate();
        em.createNativeQuery("insert into student (id, name, age) select x, random_uuid(), 30 || x from system_range(0, "+ count +")").executeUpdate();
    }

    public void iterateOverAll() {
        Session session = em.unwrap(Session.class);
        Query<Student> query = session.createQuery("SELECT s FROM Student s", Student.class);

        ScrollableResults results = query.setFetchSize(100).setReadOnly(true).setCacheable(false).scroll(ScrollMode.FORWARD_ONLY);

        while (results.next()) {
            Student s = (Student) results.get()[0];
            session.detach(s);
            System.out.println(s);
        }
    }

    public void forEach(Query<Student> query, UnaryOperator<Student> lambda) {
        Session session = em.unwrap(Session.class);
        ScrollableResults results = query.setFetchSize(100).setReadOnly(true).setCacheable(false).scroll(ScrollMode.FORWARD_ONLY);

        while (results.next()) {
            Student student = (Student) results.get()[0];
            session.detach(student);
            lambda.apply(student);
        }
    }
}
