package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

import java.util.List;

public interface TeacherTransactionAccess {

    List listAllTeachers();

    Teacher addTeacher(Teacher teacher);

    void removeTeacher(String teacher);


}
