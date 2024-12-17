import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:college.odb");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Create Students and Courses
            Student student1 = new Student("Alice", "Computer Science");
            Student student2 = new Student("Bob", "Mathematics");

            Course course1 = new Course("Algorithms", 3);
            Course course2 = new Course("Data Structures", 4);

            // Enrollments
            Enrollment enrollment1 = new Enrollment(student1, course1, new Date());
            Enrollment enrollment2 = new Enrollment(student2, course2, new Date());

            student1.addEnrollment(enrollment1);
            student2.addEnrollment(enrollment2);

            course1.addEnrollment(enrollment1);
            course2.addEnrollment(enrollment2);

            // Persist data
            em.persist(student1);
            em.persist(student2);
            em.persist(course1);
            em.persist(course2);

            em.getTransaction().commit();

            // Query Students
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            List<Student> students = query.getResultList();
            for (Student student : students) {
                System.out.println(student);
            }

            // Query Enrollments
            TypedQuery<Enrollment> enrollmentQuery = em.createQuery("SELECT e FROM Enrollment e", Enrollment.class);
            List<Enrollment> enrollments = enrollmentQuery.getResultList();
            for (Enrollment enrollment : enrollments) {
                System.out.println(enrollment);
            }

        } finally {
            em.close();
            emf.close();
        }
    }
}
