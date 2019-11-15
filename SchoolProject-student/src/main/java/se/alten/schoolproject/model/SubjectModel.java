package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Subject;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectModel {
    private Long id;
    private String title;

    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setTitle(subjectToAdd.getTitle());
        return subjectModel;
    }

    public List toModelList(List<Subject> sub){
        List<SubjectModel> modelList = new ArrayList<>();

        sub.forEach(temp -> {
            SubjectModel sm = new SubjectModel();
            sm.setId(null);
            sm.setTitle(temp.getTitle());

            modelList.add(sm);

        });
        return modelList;
    }
}
