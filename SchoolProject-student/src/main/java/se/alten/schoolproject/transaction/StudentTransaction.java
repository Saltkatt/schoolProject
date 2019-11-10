package se.alten.schoolproject.transaction;

import org.jboss.logging.Logger;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
        //return entityManager.createNamedQuery("Student.findAll", Student.class).getResultList();
        String select = "SELECT s from Student s";
        Query query = entityManager.createQuery(select);

        return query.getResultList();
    }

    public List listStudentByName(String student){
        String selectName = "SELECT s FROM Student s WHERE s.firstname = :firstname";
        Query query = entityManager.createQuery(selectName);

        System.out.println(logger);
        return query.setParameter("firstname", student).getResultList();
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
        //JPQL Query
        Query query = entityManager.createQuery(deleteStudent);

        //Native Query
        //Query query = entityManager.createNativeQuery("DELETE FROM student WHERE email = :email", Student.class);

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
