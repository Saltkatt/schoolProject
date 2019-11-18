package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

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
    private Subject subject = new Subject();
    private SubjectModel subjectModel = new SubjectModel();
    private Teacher teacher = new Teacher();
    private TeacherModel teacherModel = new TeacherModel();


    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;

    @Inject
    TeacherTransactionAccess teacherTransactionAccess;



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

            List<Subject> subjects = subjectTransactionAccess.getSubjectByName(studentToAdd.getSubjects());

            subjects.forEach(sub -> {
                studentToAdd.getSubject().add(sub);
            });

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

    @Override
    public List<Subject> listAllSubjects() {
       /* System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        List<Subject> sm = subjectTransactionAccess.listAllSubjects();
        sm.forEach(t -> {
                System.out.println(t.getTitle());
            return ;
            });
        System.out.println(sm);
        System.out.println("##########################################################################################");*/

        return  subjectModel.toModelList(subjectTransactionAccess.listAllSubjects());
    }

    @Override
    public SubjectModel addSubject(String newSubject) {
        Subject subjectToAdd = subject.toEntity(newSubject);
        subjectTransactionAccess.addSubject(subjectToAdd);
        return subjectModel.toModel(subjectToAdd);
    }

    @Override
    public List listAllTeachers() {
      return teacherModel.toModelList(teacherTransactionAccess.listAllTeachers());
    }

    @Override
    public TeacherModel findTeacherByEmail(String email){

        List<Teacher> originalList = teacherTransactionAccess.listAllTeachers();
        TeacherModel findEmail;

        //For-each loop through originalList,
        for (Teacher t: originalList) {
            //if email from orginalList equals input email,
            if(t.getEmail().equals(email)){
                //put information s in a new teacherModel
                findEmail = teacherModel.toModel(t);
                //return the new model
                return findEmail;
            }
        }
        //if not equal to email return empty model
        return findEmail = new TeacherModel();
    }

    @Override
    public TeacherModel addTeacher(String newTeacher) {

        Teacher teacherToAdd = teacher.toEntity(newTeacher);

        boolean checkForEmptyVariables =
                Stream.of(teacherToAdd.getFirstname(),
                        teacherToAdd.getLastname(),
                        teacherToAdd.getEmail())
                        .anyMatch(String::isBlank);

        //if boolean is true set firstname to "empty"
        if (checkForEmptyVariables) {
            teacherToAdd.setFirstname("empty");
            return teacherModel.toModel(teacherToAdd);

            //if email exists det firstname to "duplicate"
        } else if (teacherToAdd.getEmail().equals(findByEmail(teacherToAdd.getEmail()).getEmail())){
            teacherToAdd.setFirstname("duplicate");
            return teacherModel.toModel(teacherToAdd);
            //else add student
        }else {
            teacherTransactionAccess.addTeacher(teacherToAdd);

            List<Subject> subjects = subjectTransactionAccess.getSubjectByName(teacherToAdd.getSubjects());

            subjects.forEach(sub -> {
                teacherToAdd.getSubject().add(sub);
            });

            return teacherModel.toModel(teacherToAdd);
        }
    }

    @Override
    public void removeTeacher(String teacherEmail) {

        if(findTeacherByEmail(teacherEmail).getEmail().equals(teacherEmail)) {
            teacherTransactionAccess.removeTeacher(teacherEmail);
        }
        else{
            throw new NotFoundException();
        }

    }

}
