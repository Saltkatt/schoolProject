package se.alten.schoolproject.entity;

import lombok.*;
import se.alten.schoolproject.model.SubjectModel;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name= "title", unique = true )
    private String title;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "subject_student",
            joinColumns=@JoinColumn(name="subject_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private Set<Student> studentSet = new HashSet<>();

    @Transient
    private List<String> studentsTransList = new ArrayList<>();

    public Subject toEntity(SubjectModel subjectModel){

        Subject subject = new Subject();
        subject.setId(subjectModel.getId());
        subject.setTitle(subjectModel.getTitle());

        return subject;
    }

    public Subject toEntity(String subjectModel) {

        List<String> temp = new ArrayList<>();

        JsonReader reader = Json.createReader(new StringReader(subjectModel));

        JsonObject jsonObject = reader.readObject();

        Subject subject = new Subject();

        if ( jsonObject.containsKey("title")) {

            subject.setTitle(jsonObject.getString("title"));
        } else {
            subject.setTitle("");
        }
        if (jsonObject.containsKey("studentSet")) {
            JsonArray jsonArray = jsonObject.getJsonArray("studentSet");
            for ( int i = 0; i < jsonArray.size(); i++ ){
                temp.add(jsonArray.get(i).toString().replace("\"", ""));
                subject.setStudentsTransList(temp);
            }
        } else {
            subject.setStudentsTransList(null);
        }

        return subject;
    }
}
