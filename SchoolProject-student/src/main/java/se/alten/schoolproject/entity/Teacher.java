package se.alten.schoolproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.alten.schoolproject.model.TeacherModel;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="teacher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher implements Serializable {

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

    @ManyToMany(mappedBy = "teacherSet", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Subject> subjectSet = new HashSet<>();

    public Teacher toEntity(TeacherModel teacherModel) {

        Teacher teacher = new Teacher();
        teacher.setFirstname(teacherModel.getFirstname());
        teacher.setLastname(teacherModel.getLastname());
        teacher.setEmail(teacherModel.getEmail());
        teacher.setId(teacherModel.getId());

        return teacher;
    }

    public Teacher toEntity(String teacherModel) {

        JsonReader reader = Json.createReader(new StringReader(teacherModel));

        JsonObject jsonObject = reader.readObject();

        Teacher teacher = new Teacher();
        if ( jsonObject.containsKey("firstname")) {
            teacher.setFirstname(jsonObject.getString("firstname"));
        } else {
            teacher.setFirstname("");
        }

        if ( jsonObject.containsKey("lastname")) {
            teacher.setLastname(jsonObject.getString("lastname"));
        } else {
            teacher.setLastname("");
        }

        if ( jsonObject.containsKey("email")) {
            teacher.setEmail(jsonObject.getString("email"));
        } else {
            teacher.setEmail("");
        }

        return teacher;
    }

}
