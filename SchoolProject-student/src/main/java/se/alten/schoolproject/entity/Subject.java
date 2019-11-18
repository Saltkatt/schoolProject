package se.alten.schoolproject.entity;

import lombok.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @ManyToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    /*@JoinTable(name = "subject_students",
            joinColumns=@JoinColumn(name="subj_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stud_id", referencedColumnName = "id"))*/
    
    private Set<Student> students = new HashSet<>();

    public Subject toEntity(String subjectModel) {
        JsonReader reader = Json.createReader(new StringReader(subjectModel));

        JsonObject jsonObject = reader.readObject();

        Subject subject = new Subject();

        if ( jsonObject.containsKey("title")) {
            subject.setTitle(jsonObject.getString("title"));

        } else {
            subject.setTitle("");
        }

        return subject;
    }
}
