package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.Local;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    //findAll
    List<Student> listAllStudents() throws Exception;

    List<Teacher> listAllTeachers() throws Exception;

    List<SubjectModel> listAllSubjects();

    //findByEmail
    Teacher getTeacherByEmail(String email);

    TeacherModel findTeacherByEmail(String email);

    Student getStudentByEmail(String email);

    StudentModel findByEmail(String email);

    //findByName
    StudentModel findByName(String firstname);

    Subject listSubjectsByName(String title);

    SubjectModel getSubjectByName(String title);

    //Create

    TeacherModel addTeacher(String teacherModel);

    StudentModel addStudent(String studentModel);

    SubjectModel addSubject(String subjectModel);

    //Delete
    void removeStudent(String student) throws NotFoundException;

    void removeTeacher(String teacher) throws NotFoundException;

    void removeSubject(String subject) throws NotFoundException;

    //Update
    void updateStudent(String forename, String lastname, String email);

    void updateStudentPartial(String studentModel);

    void updateStudentToSubject(String title, String studentEmail);

    void updateTeacherToSubject(String title, String teacherEmail);




}
