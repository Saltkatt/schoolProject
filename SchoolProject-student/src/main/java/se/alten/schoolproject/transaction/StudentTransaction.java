package se.alten.schoolproject.transaction;

import org.jboss.logging.Logger;
import se.alten.schoolproject.entity.Student;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;


@Stateless
@Default
public class StudentTransaction implements StudentTransactionAccess{

    public static final Logger logger = Logger.getLogger(
            StudentTransaction.class.getName());

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllStudents() {
        String select = "SELECT s from Student s";
        Query query = entityManager.createQuery(select);
        return query.getResultList();
    }

    @Override
    public Student studentByEmail(String email){
        String queryStr = "SELECT s FROM Student s WHERE s.email = :email";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public Student addStudent(Student studentToAdd) {
        try {
            entityManager.persist(studentToAdd);
            entityManager.flush();
            return studentToAdd;
        } catch ( PersistenceException pe ) {
            studentToAdd.setFirstname("duplicate");
            return studentToAdd;
        }
    }

    @Override
    public void removeStudent(String student) {
        String deleteStudent = "DELETE FROM Student s WHERE s.email = :email";
        Query query = entityManager.createQuery(deleteStudent);

        query.setParameter("email", student)
             .executeUpdate();
    }

    @Override
    public void updateStudent(String firstname, String lastname, String email) {
        String updateStudent = "UPDATE student SET firstname = :firstname, lastname = :lastname WHERE email = :email";
        Query updateQuery = entityManager.createNativeQuery(updateStudent, Student.class);
        updateQuery.setParameter("firstname", firstname)
                   .setParameter("lastname", lastname)
                   .setParameter("email", email)
                   .executeUpdate();
    }

    @Override
    public void updateStudentPartial(Student student) {
        String selectStudentToUpdate = "SELECT s FROM Student s WHERE s.email = :email";
        String updateFirstName = "UPDATE Student SET firstname = :studentFirstname WHERE email = :email";
        Student studentFound = (Student)entityManager.createQuery(selectStudentToUpdate)
                .setParameter("email", student.getEmail()).getSingleResult();

        Query query = entityManager.createQuery(updateFirstName);
        query.setParameter("studentFirstname", student.getFirstname())
                .setParameter("email", studentFound.getEmail())
                .executeUpdate();
    }
}
