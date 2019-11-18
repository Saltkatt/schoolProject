package se.alten.schoolproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.alten.schoolproject.entity.Teacher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherModel {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();

    public List toModelList(List<Teacher> teachers){
        List<TeacherModel>modelList = new ArrayList<>();

        teachers.forEach(temp -> {
            TeacherModel tm = new TeacherModel();
            tm.setId(null);
            tm.setFirstname(temp.getFirstname());
            tm.setLastname(temp.getLastname());
            tm.setEmail(temp.getEmail());
            temp.getSubject().forEach(subject -> {
                tm.subjects.add(subject.getTitle());
            });
            modelList.add(tm);

        });
        return modelList;
    }

    public TeacherModel toModel(Teacher teacher) {
         TeacherModel teacherModel = new TeacherModel();

        teacherModel.setFirstname(teacher.getFirstname());
        teacherModel.setLastname(teacher.getLastname());
        teacherModel.setEmail(teacher.getEmail());
        teacher.getSubject().forEach(subject -> {
            teacherModel.subjects.add(subject.getTitle());
        });

        return teacherModel;

    }
}
