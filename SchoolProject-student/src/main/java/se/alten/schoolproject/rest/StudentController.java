package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless //Efter ett anrop gjorts töms minnet
@NoArgsConstructor // Kräver tom konstruktor, konstruktor, getter & setter - undvik data och toString -
@Path("/student")
public class StudentController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces({"application/JSON"})
    public Response showStudents() {
        //Ändra så att id inte visas när man hämtar i insomnia: id ska vara null.
        try {
            List students = sal.listAllStudents();
            return Response.ok(students).build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Produces({"application/JSON"})
    @Path("{firstname}")
    public Response getStudentByName(@PathParam("firstname") String firstname){
        try {
            List student = sal.listStudentByName(firstname);
            return Response.ok(student).build();

        }catch (IllegalArgumentException e){
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Name does not exist!\"}").build();

        }catch (Exception e) {
            e.getMessage();
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/JSON"})
    public Response addStudent(String studentModel) { //hämtar model, tar bort som inte ska visas för användaren.
        try {

            StudentModel answer = sal.addStudent(studentModel);

            switch ( answer.getFirstname()) {
                case "empty":
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
                case "duplicate":
                    return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Email already registered!\"}").build();
                default:
                    return Response.ok(answer).build();
            }
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{email}")
    public Response deleteUser( @PathParam("email") String email) {
        try {
            sal.removeStudent(email);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    public void updateStudent(
            @QueryParam("firstname") String firstname,
            @QueryParam("lastname") String lastname,
            @QueryParam("email") String email) {

        sal.updateStudent(firstname, lastname, email);
    }

    @PATCH
    public void updatePartialAStudent(String studentModel) {
        sal.updateStudentPartial(studentModel);
    }
}
