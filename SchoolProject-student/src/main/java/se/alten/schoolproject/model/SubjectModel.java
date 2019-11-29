package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel {

    private Long id;
    private String title;
    private Set<String> students = new HashSet<>();

    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setId(subjectToAdd.getId());
        subjectModel.setTitle(subjectToAdd.getTitle());
        subjectToAdd.getStudentSet().forEach(student -> {
            subjectModel.students.add(student.getEmail());
        });
        return subjectModel;
    }

    public List toModelList(List<Subject> sub){
        List<SubjectModel> modelList = new ArrayList<>();

        sub.forEach(temp -> {
            SubjectModel sm = new SubjectModel();
            sm.setId(null);
            sm.setTitle(temp.getTitle());
            temp.getStudentSet().forEach(student -> {
                sm.students.add(student.getEmail());
            });

            modelList.add(sm);

        });
        return modelList;
    }
}
