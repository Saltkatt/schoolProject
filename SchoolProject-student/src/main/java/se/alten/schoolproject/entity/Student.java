package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.StudentModel;

import javax.json.*;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.*;

@Entity
@Table(name="student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(mappedBy = "studentSet", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Subject> subjectSet = new HashSet<>();

    public Student toEntity(StudentModel studentModel) {

        Student student = new Student();
        student.setFirstname(studentModel.getFirstname());
        student.setLastname(studentModel.getLastname());
        student.setEmail(studentModel.getEmail());
        student.setId(studentModel.getId());

        return student;
    }

    public Student toEntity(String studentModel) {

        JsonReader reader = Json.createReader(new StringReader(studentModel));

        JsonObject jsonObject = reader.readObject();

        Student student = new Student();
        if ( jsonObject.containsKey("firstname")) {
            student.setFirstname(jsonObject.getString("firstname"));
        } else {
            student.setFirstname("");
        }

        if ( jsonObject.containsKey("lastname")) {
            student.setLastname(jsonObject.getString("lastname"));
        } else {
            student.setLastname("");
        }

        if ( jsonObject.containsKey("email")) {
            student.setEmail(jsonObject.getString("email"));
        } else {
            student.setEmail("");
        }

        return student;
    }
}
