package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.jpa.StudentRepository;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateQuery;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    EntityManager em;

    @Autowired
    StudentRepository studentRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    void myTest() {
        em.createNativeQuery("insert into student (id, name, age) select x, random_uuid(), 30 || x from system_range(0, 150)").executeUpdate();

        Runtime.getRuntime().gc();
        System.out.println("Free memory before: " + Runtime.getRuntime().freeMemory());

        Session session = em.unwrap(Session.class);
        Query<Student> query = session.createQuery("SELECT s FROM Student s", Student.class);

        ScrollableResults results = query.setFetchSize(100).setReadOnly(true).setCacheable(false).scroll(ScrollMode.FORWARD_ONLY);

        while (results.next()) {
            Student s = (Student) results.get()[0];
            session.detach(s);
            System.out.println(s);
        }

        Runtime.getRuntime().gc();
        System.out.println("Free memory after: " + Runtime.getRuntime().freeMemory());

        /*
        List<Student> all = studentRepository.findAll();

        System.out.println();
        System.out.println();
        System.out.println("**************** RESULTS:");
        System.out.println(all);
        System.out.println();
        System.out.println();
         */
    }

}
