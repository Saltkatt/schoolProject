package se.alten.schoolproject.model;


import lombok.*;
import se.alten.schoolproject.entity.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();

    /**
     * Skapad tillsammans med Filip Christoffersson.
     * Skapar en lista utan id nummer med hjälp av StudentModel.
     * @param
     * @return
     */

    public StudentModel onlyByName(String firstname, String lastname){
        StudentModel student = new StudentModel();
        student.setFirstname(firstname);
        student.setLastname(lastname);
        return student;
    }

    public List<StudentModel> toModelList(List<Student> students){
        List<StudentModel>modelList = new ArrayList<>();

        students.forEach(temp -> {
            StudentModel sm = new StudentModel();
            sm.setId(null);
            sm.setFirstname(temp.getFirstname());
            sm.setLastname(temp.getLastname());
            sm.setEmail(temp.getEmail());
            temp.getSubjectSet().forEach(subject -> {
                sm.subjects.add(subject.getTitle());
            });
            modelList.add(sm);

        });
        return modelList;
    }

    public StudentModel toModel(Student student) {
        StudentModel studentModel = new StudentModel();

        studentModel.setId(student.getId());
        studentModel.setFirstname(student.getFirstname());
        studentModel.setLastname(student.getLastname());
        studentModel.setEmail(student.getEmail());
        student.getSubjectSet().forEach(s ->{
            studentModel.subjects.add(s.getTitle());
        });

        return studentModel;
    }

}
