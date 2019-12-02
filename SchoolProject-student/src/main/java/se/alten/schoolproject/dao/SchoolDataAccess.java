package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

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
    private Teacher teacher = new Teacher();
    private TeacherModel teacherModel = new TeacherModel();

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;

    @Inject
    TeacherTransactionAccess teacherTransactionAccess;


    //findAll
    @Override
    public List<Student> listAllStudents()throws NotFoundException{
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        return studentModel.toModelList(studentTransactionAccess.listAllStudents());
    }

    @Override
    public List<Teacher> listAllTeachers() throws Exception {
        return teacherModel.toModelList(teacherTransactionAccess.listAllTeachers());
    }

    @Override
    public List<SubjectModel> listAllSubjects() {

        System.out.println("##############################################################################################");
        List<Subject> sm = subjectTransactionAccess.listAllSubjects();
        sm.forEach(t -> {
            System.out.println(t.getTitle() + " from List in SchoolDataAccess");
            System.out.println(t.getStudentSet() + "from List in SchoolDataAccess\"");
            System.out.println(t.toString() + "from List in SchoolDataAccess\"");
            return ;
        });

        List<SubjectModel> t = subjectModel.toModelList(subjectTransactionAccess.listAllSubjects());

        return t;
    }

    //findByEmail
    /**
     * Method used in updateStudentToSubject() to allow for join table.
     * @param email
     * @return
     */
    @Override
    public Student getStudentByEmail(String email){

        Student foundStudent = studentTransactionAccess.studentByEmail(email);

        return foundStudent;
    }

    /**
     * Method used in updateTeacherToSubject() to allow for join table.
     * @param email
     * @return
     */
    @Override
    public Teacher getTeacherByEmail(String email) {

        Teacher foundTeacher = teacherTransactionAccess.findTeacherByEmail(email);

        return foundTeacher;
    }

    @Override
    public StudentModel findByEmail(String email){

        List<Student> originalStudentList = studentTransactionAccess.listAllStudents();
        StudentModel findEmail;

        //For-each loop through originalList,
        for (Student s: originalStudentList) {
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

    @Override
    public TeacherModel findTeacherByEmail(String email) {

        List<Teacher> originalTeacherList = teacherTransactionAccess.listAllTeachers();
        TeacherModel findByEmail;

        for (Teacher t:originalTeacherList) {
            if(t.getEmail().equals(email)){
                findByEmail = teacherModel.toModel(t);
                return findByEmail;
            }
        }
        return findByEmail = new TeacherModel();
    }

    //findByName
    @Override
    public StudentModel findByName(String firstname){

        List<Student> originalStudentList = studentTransactionAccess.listAllStudents();
        StudentModel findByName;

        //For-each loop through originalList,
        for(Student s: originalStudentList) {
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

    /**
     * Only used in updateStudentToSubject()
     * @param title
     * @return
     */
    @Override
    public Subject listSubjectsByName(String title){

        Subject foundSubject = subjectTransactionAccess.listSubjectsByTitle(title);

        return foundSubject;
    }

    @Override
    public SubjectModel getSubjectByName(String title){
        List<Subject> originalSubjectList = subjectTransactionAccess.listAllSubjects();
        SubjectModel findSubject;

        for (Subject s: originalSubjectList) {
            if(s.getTitle().equals(title)){
                findSubject = subjectModel.toModel(s);
                return findSubject;
            }
        }
        return findSubject = new SubjectModel();

    }

    @Override
    public TeacherModel findTeacherByName(String firstname) {
        List<Teacher> originalTeacherList = teacherTransactionAccess.listAllTeachers();
        TeacherModel findByName;

        for(Teacher t: originalTeacherList) {
            if(t.getFirstname().equals(firstname)){
                findByName = teacherModel.toModel(t);
                return findByName;
            }
        }
        return findByName = new TeacherModel();
    }


    //Create
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

        System.out.println("(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((");


        //if boolean is true set firstname to "empty"
        if (checkForEmptyVariables) {
            studentToAdd.setFirstname("empty");
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            return studentModel.toModel(studentToAdd);

            //if email exists det firstname to "duplicate"
        } else if (studentToAdd.getEmail().equals(findByEmail(studentToAdd.getEmail()).getEmail())){
            studentToAdd.setFirstname("duplicate");
            return studentModel.toModel(studentToAdd);
            //else add student
        }else {

            studentTransactionAccess.addStudent(studentToAdd);
            System.out.println(studentModel.toModel(studentToAdd));
            return studentModel.toModel(studentToAdd);
        }
    }

    @Override
    public TeacherModel addTeacher(String newTeacher) {
        Teacher teacherToAdd = teacher.toEntity(newTeacher);

        boolean checkForEmptyVariables =
                Stream.of(teacherToAdd.getFirstname(),
                        teacherToAdd.getLastname(),
                        teacherToAdd.getEmail())
                        .anyMatch(String::isBlank);

        if (checkForEmptyVariables) {
            teacherToAdd.setFirstname("empty");
            return teacherModel.toModel(teacherToAdd);

        } else if (teacherToAdd.getEmail().equals(findByEmail(teacherToAdd.getEmail()).getEmail())){
            teacherToAdd.setFirstname("duplicate");
            return teacherModel.toModel(teacherToAdd);
        }else {
            teacherTransactionAccess.addTeacher(teacherToAdd);
            return teacherModel.toModel(teacherToAdd);
        }

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

    //Delete
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
    public void removeTeacher(String teacherEmail) {
        if(findTeacherByEmail(teacherEmail).getEmail().equals(teacherEmail)){
            teacherTransactionAccess.removeTeacher(teacherEmail);
        }
        else{
            throw new NotFoundException();
        }
    }

    @Override
    public void removeSubject(String subjectTitle) throws NotFoundException {

        if(getSubjectByName(subjectTitle).getTitle().equals(subjectTitle)) {
            subjectTransactionAccess.removeSubject(subjectTitle);
        }
        else{
            throw new NotFoundException();
        }
    }


    //Update
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
    public void updateStudentToSubject(String title, String studentEmail) {

        String email = "No email";

        JsonReader reader = Json.createReader(new StringReader(studentEmail));
        JsonObject jsonObject = reader.readObject();

        if (jsonObject.containsKey("email")) {
            JsonValue jsonValue = jsonObject.getValue("/email");
            email = jsonValue.toString().replace("\"", "");
        }

        Student student = getStudentByEmail(email);
        Subject subject = listSubjectsByName(title);

        subject.getStudentSet().add(student);

    }

    @Override
    public void updateTeacherToSubject(String title, String teacherEmail){
        String email = "No email";

        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        JsonReader reader = Json.createReader(new StringReader(teacherEmail));
        JsonObject jsonObject = reader.readObject();

        if (jsonObject.containsKey("email")) {
            JsonValue jsonValue = jsonObject.getValue("/email");
            email = jsonValue.toString().replace("\"", "");
        }

        Teacher teacher = getTeacherByEmail(email);
        Subject subject = listSubjectsByName(title);

        System.out.println("...........................................................................................");

        subject.getTeacherSet().add(teacher);

        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
    }




}
