package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    private Student student = new Student();
    private StudentModel studentModel = new StudentModel();
    private Subject subject = new Subject();
    private SubjectModel subjectModel = new SubjectModel();

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;



    @Override
    public List<Student> listAllStudents()throws NotFoundException{

        return studentModel.toModelList(studentTransactionAccess.listAllStudents());
    }

    @Override
    public StudentModel findByName(String firstname){

        List<Student> originalList = studentTransactionAccess.listAllStudents();
        StudentModel findByName;

        //For-each loop through originalList,
        for(Student s: originalList) {
            //if firstname from orginalList equals input firstname,
            if(s.getFirstname().equals(firstname)){
                //put information s in a new studentModel
                findByName = studentModel.toModel(s);
                //return the new model
                return findByName;
            }
        }
        //if not equal to firstname return empty model
        return findByName = new StudentModel();
    }

    @Override
    public StudentModel findByEmail(String email){

        List<Student> originalList = studentTransactionAccess.listAllStudents();
        StudentModel findEmail;

        //For-each loop through originalList,
        for (Student s: originalList) {
            //if email from orginalList equals input email,
            if(s.getEmail().equals(email)){
                //put information s in a new studentModel
                findEmail = studentModel.toModel(s);
                //return the new model
                return findEmail;
            }
        }
        //if not equal to email return empty model
        return findEmail = new StudentModel();
    }


    /**
     * Lösning resultat av parprogrammering med Filip Christoffersson.
     * @param newStudent
     * @return
     */
    @Override
    public StudentModel addStudent(String newStudent) {

        Student studentToAdd = student.toEntity(newStudent);
        boolean checkForEmptyVariables =
                Stream.of(studentToAdd.getFirstname(),
                        studentToAdd.getLastname(),
                        studentToAdd.getEmail())
                        .anyMatch(String::isBlank);

        //if boolean is true set firstname to "empty"
        if (checkForEmptyVariables) {
            studentToAdd.setFirstname("empty");
            return studentModel.toModel(studentToAdd);

            //if email exists det firstname to "duplicate"
        } else if (studentToAdd.getEmail().equals(findByEmail(studentToAdd.getEmail()).getEmail())){
            studentToAdd.setFirstname("duplicate");
            return studentModel.toModel(studentToAdd);
            //else add student
        }else {
     /*       List<Subject> subjects = subjectTransactionAccess.getSubjectByName(studentToAdd.getSubjects());

            subjects.forEach(sub -> {
                studentToAdd.getSubject().add(sub);
            });*/

            studentTransactionAccess.addStudent(studentToAdd);

            return studentModel.toModel(studentToAdd);
        }
    }

    @Override
    public void removeStudent(String studentEmail) {

        if(findByEmail(studentEmail).getEmail().equals(studentEmail)) {
            studentTransactionAccess.removeStudent(studentEmail);
        }
        else{
            throw new NotFoundException();
        }

    }

    @Override
    public void updateStudent(String firstname, String lastname, String email) {

        if(email.equals(findByEmail(email).getEmail())){
            studentTransactionAccess.updateStudent(firstname, lastname, email);
        }
        else{
            throw new BadRequestException();
        }

    }

    @Override
    public void updateStudentPartial(String studentModel) {

        Student studentToUpdate = student.toEntity(studentModel);

        if(studentToUpdate.getFirstname().isBlank()|| studentToUpdate.getEmail().isBlank()){
            throw new BadRequestException();
        }
        else {
            studentTransactionAccess.updateStudentPartial(studentToUpdate);
        }

    }

    @Override
    public List<SubjectModel> listAllSubjects() {

        List<Subject> sm = subjectTransactionAccess.listAllSubjects();
        sm.forEach(t -> {
                System.out.println(t.getTitle() + " from List in SchoolDataAccess");
            return ;
            });

        List<SubjectModel> t = subjectModel.toModelList(subjectTransactionAccess.listAllSubjects());

        return t;
    }

    public SubjectModel getSubjectByName(String title){
        List<Subject> subjectList = subjectTransactionAccess.listAllSubjects();
        SubjectModel findSubject = new SubjectModel();

        for(Subject s: subjectList){
            if(s.getTitle().equals(title)){
                findSubject = subjectModel.toModel(s);
            }
        }
        return findSubject;
    }

    @Override
    public SubjectModel addSubject(String newSubject) {

        Subject subjectToAdd = subject.toEntity(newSubject);
        subjectTransactionAccess.addSubject(subjectToAdd);
        List<Student> studentsWithSubjects = new ArrayList<>();

            studentsWithSubjects.forEach(student -> {
                subjectToAdd.getStudentSet().add(student);
            });

        return subjectModel.toModel(subjectToAdd);
    }

    @Override
    public void updateSubjectPartial(String title, String studentEmail) {

        /**
         * Todo: Lägga till en student till ett subject.
         * Todo: Subject toEntity behöver kunna ta emot ett student objekt.
         * Todo: updatePartialSubject ska uppdatera subject genom att lägga till eller ta bort ett student/teacher objekt.
         *
         */

        String email = "No email";

        JsonReader reader = Json.createReader(new StringReader(studentEmail));
        JsonObject jsonObject = reader.readObject();
        Subject subject = new Subject();

        if (jsonObject.containsKey("email")) {
            JsonValue jsonValue = jsonObject.getValue("/email");
            email = jsonValue.toString().replace("\"", "");
            System.out.println("JSON re-write: " + email + " ---------------------------------------------------------");
        }

        SubjectModel subjectRetrieved = getSubjectByName(title);
        StudentModel studentFound = findByEmail(email);

        Subject sub = subject.toEntity(subjectRetrieved);
        //student entity
        Student stud = student.toEntity(studentFound);
        //spara mot databas med .add
        sub.getStudentSet().add(stud);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@" + sub + "@@@@@@@@@@@@@@@@@@@");

        


        /*System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Beginning of updatePartial!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        SubjectModel subjectName = getSubjectByName(title);
        String email = "No email";

        JsonReader reader = Json.createReader(new StringReader(studentEmail));
        JsonObject jsonObject = reader.readObject();
        Subject subject = new Subject();

        if (jsonObject.containsKey("email")) {
            JsonValue jsonValue = jsonObject.getValue("/email");
            email = jsonValue.toString().replace("\"", "");
            System.out.println("JSON re-write: " + email + " ---------------------------------------------------------");
        }

        StudentModel stu = findByEmail(email);
        Student test = student.toEntity(stu);

        List<Student> studentsWithSubjects = new ArrayList<>();
        //subjectName.getStudentSet().add(test);

        subject.getStudentSet().add(test);


        System.out.println("subjectName: " + subjectName);

        System.out.println("----------------------------------- Before return ----------------------------------");*/

    }


}
