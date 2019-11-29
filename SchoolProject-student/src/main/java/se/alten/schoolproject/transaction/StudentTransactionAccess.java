package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StudentTransactionAccess {
    List listAllStudents();

    Student studentByEmail(String email);

    Student addStudent(Student studentToAdd);

    void removeStudent(String student);

    void updateStudent(String firstname, String lastname, String email);

    void updateStudentPartial(Student studentToUpdate);

}
