package se.alten.schoolproject.dao;

import org.jboss.logging.Logger;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.transaction.StudentTransaction;
import se.alten.schoolproject.transaction.StudentTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {
    public static final Logger logger = Logger.getLogger(
            SchoolDataAccess.class.getName());

    private Student student = new Student();
    private StudentModel studentModel = new StudentModel();

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Override
    public List<Student> listAllStudents()throws IllegalArgumentException{

        return studentTransactionAccess.listAllStudents();
    }

    @Override
    public List listStudentByName(String firstname){

        List<Student> temp = studentTransactionAccess.listAllStudents();
        List<Student> findByName = new ArrayList();

        for(Student s: temp) {
            if(s.getFirstname().equals(firstname)){
                    findByName.add(s);
            }
        }
        return findByName;
    }

    @Override
    public List findByEmail(String email){
        List<Student> findByEmail = new ArrayList();
        List<Student> temp = studentTransactionAccess.listAllStudents();

        for (Student s: temp) {
            if(s.getEmail().equals(email)){
                findByEmail.add(s);
            }
        }
        return findByEmail;
    }


    @Override
    public StudentModel addStudent(String newStudent) {

        Student studentToAdd = student.toEntity(newStudent);
        boolean checkForEmptyVariables =
                Stream.of(studentToAdd.getFirstname(),
                        studentToAdd.getLastname(),
                        studentToAdd.getEmail())
                        .anyMatch(String::isBlank);

       /* if(checkForEmptyVariables){
            return studentModel.toModel(studentToAdd);
        }
       */

       if (checkForEmptyVariables) {
            studentToAdd.setFirstname("empty");
            return studentModel.toModel(studentToAdd);
        } else {
            studentTransactionAccess.addStudent(studentToAdd);
            return studentModel.toModel(studentToAdd);
        }
    }

    @Override
    public void removeStudent(String studentEmail) {
        studentTransactionAccess.removeStudent(studentEmail);
    }

    @Override
    public void updateStudent(String firstname, String lastname, String email) {
        studentTransactionAccess.updateStudent(firstname, lastname, email);
    }

    @Override
    public void updateStudentPartial(String studentModel) {
        Student studentToUpdate = student.toEntity(studentModel);
        studentTransactionAccess.updateStudentPartial(studentToUpdate);
    }
}
