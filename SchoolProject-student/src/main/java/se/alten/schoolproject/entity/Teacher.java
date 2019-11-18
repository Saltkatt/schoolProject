package se.alten.schoolproject.entity;

import lombok.*;
import javax.json.*;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.*;

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

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_subject",
            joinColumns=@JoinColumn(name="teach_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subj_id", referencedColumnName = "id"))
    private Set<Subject> subject = new HashSet<>();

    @Transient
    private List<String> subjects = new ArrayList<>();

    public Teacher toEntity(String teacherModel) {

        List<String> temp = new ArrayList<>();

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

        if (jsonObject.containsKey("subject")) {
            JsonArray jsonArray = jsonObject.getJsonArray("subject");
            for ( int i = 0; i < jsonArray.size(); i++ ){
                temp.add(jsonArray.get(i).toString().replace("\"", ""));
                teacher.setSubjects(temp);
            }
        } else {
            teacher.setSubjects(null);
        }

        return teacher;
    }
}
