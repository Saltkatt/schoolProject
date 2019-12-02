package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Teacher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherModel {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();

    public List<TeacherModel> toModelList(List<Teacher> teachers){
        List<TeacherModel>modelList = new ArrayList<>();

        teachers.forEach(temp -> {
            TeacherModel teacherModel = new TeacherModel();
            teacherModel.setId(null);
            teacherModel.setFirstname(temp.getFirstname());
            teacherModel.setLastname(temp.getLastname());
            teacherModel.setEmail(temp.getEmail());
            temp.getSubjectSet().forEach(subject -> {
                teacherModel.subjects.add(subject.getTitle());

            });
            modelList.add(teacherModel);

        });
        return modelList;
    }

    public TeacherModel toModel(Teacher teacher) {

        TeacherModel teacherModel = new TeacherModel();

        teacherModel.setId(teacher.getId());
        teacherModel.setFirstname(teacher.getFirstname());
        teacherModel.setLastname(teacher.getLastname());
        teacherModel.setEmail(teacher.getEmail());
        teacher.getSubjectSet().forEach(s ->{
            teacherModel.subjects.add(s.getTitle());
        });

        return teacherModel;
    }
}
