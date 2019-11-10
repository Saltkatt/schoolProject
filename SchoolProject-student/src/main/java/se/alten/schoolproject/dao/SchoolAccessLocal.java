package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Local;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List<Student> listAllStudents() throws Exception;

    List findByName(String firstname);

    List findByEmail(String email);

    StudentModel addStudent(String studentModel);

    void removeStudent(String student) throws NotFoundException;

    void updateStudent(String forename, String lastname, String email);

    void updateStudentPartial(String studentModel);

}
