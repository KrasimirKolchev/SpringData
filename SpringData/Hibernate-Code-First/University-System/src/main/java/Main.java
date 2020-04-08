import entities.Course;
import entities.Student;
import entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        Teacher teacher = new Teacher();
        teacher.setFirstName("Petar");
        teacher.setLastName("Petrov");
        teacher.setPhoneNumber("0877456789");
        teacher.setCourses(new HashSet<>());

        Course course1 = new Course();
        course1.setName("First course");
        course1.setDescription("First course description");
        course1.setCredits(1);
        course1.setTeacher(teacher);
        course1.setStudents(new HashSet<>());

        Course course2 = new Course();
        course2.setName("First course");
        course2.setDescription("First course description");
        course2.setCredits(1);
        course2.setTeacher(teacher);
        course2.setStudents(new HashSet<>());

        Student student = new Student();
        student.setFirstName("Ivan");
        student.setLastName("Ivanov");
        student.setPhoneNumber("0899123456");
        student.setCourses(new HashSet<>());

        Student student1 = new Student();
        student1.setFirstName("Georgi");
        student1.setLastName("Georgiev");
        student1.setPhoneNumber("0899654321");
        student1.setCourses(new HashSet<>());

        course1.getStudents().add(student);
        course1.getStudents().add(student1);
        course2.getStudents().add(student);

        student.getCourses().add(course1);
        student.getCourses().add(course2);
        student1.getCourses().add(course1);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("university1");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();

            manager.persist(teacher);
            manager.persist(student);
            manager.persist(student1);
            manager.persist(course1);
            manager.persist(course2);

            manager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            manager.getTransaction().rollback();
        }

        manager.clear();

    }
}
