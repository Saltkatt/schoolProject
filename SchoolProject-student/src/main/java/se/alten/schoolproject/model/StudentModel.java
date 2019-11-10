package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;

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

    public StudentModel toModel(Student student) {
        StudentModel studentModel = new StudentModel();

        studentModel.setFirstname(student.getFirstname());
        studentModel.setLastname(student.getLastname());
        studentModel.setEmail(student.getEmail());

        return studentModel;


    /*    switch (student.getFirstname()) {
            case "empty":
                studentModel.setFirstname("empty");
                return studentModel;
            case "duplicate":
                studentModel.setFirstname("duplicate");
                return studentModel;
            default:
                studentModel.setFirstname(student.getFirstname());
                studentModel.setLastname(student.getLastname());
                studentModel.setEmail(student.getEmail());
                return studentModel;
        }*/
    }
}
