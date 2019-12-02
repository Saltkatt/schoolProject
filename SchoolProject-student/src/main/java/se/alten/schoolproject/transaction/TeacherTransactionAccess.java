package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TeacherTransactionAccess {

    List listAllTeachers();

    Teacher findTeacherByEmail(String email);

    Teacher addTeacher(Teacher teacherToAdd);

    void removeTeacher(String teacher);

    //void updateTeacher(String firstname, String lastname, String email);
}
