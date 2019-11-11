package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Skapad tillsammans med Filip Christoffersson.
     * Skapar en lista utan id nummer med hjälp av StudentModel.
     * @param list
     * @return
     */
    public List toModelList(List<Student> list){
        List<StudentModel>modelList = new ArrayList<>();

        list.forEach(temp -> {
            StudentModel studentModel = new StudentModel();
            studentModel.setId(null);
            studentModel.setFirstname(temp.getFirstname());
            studentModel.setLastname(temp.getLastname());
            studentModel.setEmail(temp.getEmail());
            modelList.add(studentModel);

        });
        return modelList;
    }

    public StudentModel toModel(Student student) {
        StudentModel studentModel = new StudentModel();

        System.out.println(student.toString());

        studentModel.setFirstname(student.getFirstname());
        studentModel.setLastname(student.getLastname());
        studentModel.setEmail(student.getEmail());

        return studentModel;

    }
}
