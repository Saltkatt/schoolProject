package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    private Student student = new Student();
    private StudentModel studentModel = new StudentModel();


    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Override
    public List<Student> listAllStudents()throws NotFoundException{

        return studentModel.toModelList(studentTransactionAccess.listAllStudents());
    }

    @Override
    public StudentModel findByName(String firstname){

        List<Student> originalList = studentTransactionAccess.listAllStudents();
        StudentModel findByName;

        //For-each loop through originalList,
        for(Student s: originalList) {
            //if firstname from orginalList equals input firstname,
            if(s.getFirstname().equals(firstname)){
                //put information s in a new studentModel
                findByName = studentModel.toModel(s);
                //return the new model
                return findByName;
            }
        }
        //if not equal to email return empty model
        return findByName = new StudentModel();
    }

    @Override
    public StudentModel findByEmail(String email){

        List<Student> originalList = studentTransactionAccess.listAllStudents();
        StudentModel findEmail;

        //For-each loop through originalList,
        for (Student s: originalList) {
            //if email from orginalList equals input email,
            if(s.getEmail().equals(email)){
                //put information s in a new studentModel
                findEmail = studentModel.toModel(s);
                //return the new model
                return findEmail;
            }
        }
        //if not equal to email return empty model
        return findEmail = new StudentModel();
    }


    /**
     * Lösning resultat av parprogrammering med Filip Christoffersson.
     * @param newStudent
     * @return
     */
    @Override
    public StudentModel addStudent(String newStudent) {

        Student studentToAdd = student.toEntity(newStudent);
        boolean checkForEmptyVariables =
                Stream.of(studentToAdd.getFirstname(),
                        studentToAdd.getLastname(),
                        studentToAdd.getEmail())
                        .anyMatch(String::isBlank);

        //if boolean is true set firstname to "empty"
        if (checkForEmptyVariables) {
            studentToAdd.setFirstname("empty");
            return studentModel.toModel(studentToAdd);

            //if email exists det firstname to "duplicate"
        } else if (studentToAdd.getEmail().equals(findByEmail(studentToAdd.getEmail()).getEmail())){
            studentToAdd.setFirstname("duplicate");
            return studentModel.toModel(studentToAdd);
            //else add student
        }else {
            studentTransactionAccess.addStudent(studentToAdd);
            return studentModel.toModel(studentToAdd);
        }
    }

    @Override
    public void removeStudent(String studentEmail) {

        if(findByEmail(studentEmail).getEmail().equals(studentEmail)) {
            studentTransactionAccess.removeStudent(studentEmail);
        }
        else{
            throw new NotFoundException();
        }

    }

    @Override
    public void updateStudent(String firstname, String lastname, String email) {

        if(email.equals(findByEmail(email).getEmail())){
            studentTransactionAccess.updateStudent(firstname, lastname, email);
        }
        else{
            throw new BadRequestException();
        }

    }

    @Override
    public void updateStudentPartial(String studentModel) {

        Student studentToUpdate = student.toEntity(studentModel);

        if(studentToUpdate.getFirstname().isBlank()|| studentToUpdate.getEmail().isBlank()){
            throw new BadRequestException();
        }
        else {
            studentTransactionAccess.updateStudentPartial(studentToUpdate);
        }

    }
}
