package com.example.demo;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.jpa.CourseRepository;
import com.example.demo.jpa.StudentRepository;
import com.example.demo.repositories.StudentSpringDataRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateQuery;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Log4j2
class DemoApplicationTests {

    @Autowired
    EntityManager em;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentSpringDataRepository studentSpringDataRepository;


    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    void myTestLombok() {
        Course pietro = new Course("Pietro");
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

    @Test
    @Transactional
    void myTestForeach() {
        Query<Student> query = em.unwrap(Session.class).createQuery("SELECT s FROM Student s", Student.class);

        studentRepository.forEach(query, (student) -> {
            System.out.println(student);
            return student;
        });
    }

    @Test
    void pageable() {
        PageRequest pageReq = PageRequest.of(0,3);
        Page<Student> firstPage = studentSpringDataRepository.findAll(pageReq);
        Page<Student> secondPage = studentSpringDataRepository.findAll(firstPage.nextPageable());

        firstPage.forEach((s) -> {
            System.out.println("*** Student: "+ s);
        });

    }

    @Test
    void pageable2() {
        int pageSize = 3;
        PageRequest pageReq = PageRequest.of(0,pageSize);
        Page<Student> page = studentSpringDataRepository.findAll(pageReq);

        do {

            page.forEach((s) -> {
                System.out.println("*** Student: "+ s);
            });

            page = studentSpringDataRepository.findAll(page.nextPageable());
        } while (page.hasNext());
    }

    @Test
    @Transactional
    void manyToMany() {
        List<Student> students = studentRepository.findAll();
        Course course = courseRepository.save(new Course("Computer Scienze"));

        for (Student student : students) {
            student.addCourse(course);
            course.addStudent(student);
            studentRepository.save(student);
        }

        courseRepository.save(course);

        List<Course> courses = courseRepository.findAll();


        for (Course c : courses) {
            if (course.getName().equals("Computer Scienze")) {
                courseRepository.forEachStudent(course, (student) -> {
                        System.out.println("-> student " + student);
                        return student;
                });
            }

        }
    }
}
