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

    List<Student> listAllStudents() throws Exception;

    List<Teacher> listAllTeachers() throws Exception;

    Teacher getTeacherByEmail(String email);

    TeacherModel addTeacher(String teacherModel);

    Student getStudentByEmail(String email);

    StudentModel findByName(String firstname);

    StudentModel findByEmail(String email);

    StudentModel addStudent(String studentModel);

    void removeStudent(String student) throws NotFoundException;

    void updateStudent(String forename, String lastname, String email);

    void updateStudentPartial(String studentModel);

    List<SubjectModel> listAllSubjects();

    SubjectModel getSubjectByName(String title);

    Subject listSubjectsByName(String title);

    SubjectModel addSubject(String subjectModel);

    void addStudentToSubject(String title, String studentEmail);

    void removeSubject(String subject) throws NotFoundException;


}
