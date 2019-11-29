package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;

import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.StudentModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/student")
public class StudentController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allStudents() {

        try {
            System.out.println("GETGETGETGETGETGETGETGETGETGETGETGET!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            List students = sal.listAllStudents();
            System.out.println(students);
            System.out.println("ENDOFGET///////////////////////////////////////////////////////////////");
            return Response.ok(students).build();
        }
        /*catch (BadRequestException e) {
            return ExceptionResponse.getResponse()
        }*/
        catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{firstname}")
    public Response studentsByName(@PathParam("firstname") String firstname){
        try {
            StudentModel student = sal.findByName(firstname);
            return Response.ok(student).build();

        }catch (Exception e) {
            e.getMessage();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find/{email}")
    public Response studentsByEmail(@PathParam("email") String email) {

        try{
            StudentModel student = sal.findByEmail(email);
            System.out.println("%%%%%%%%%%%%%%%%%% " + student + " %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            return Response.ok(student).build();
        }catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Student could not be found\"}").build();
        }

    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(String studentModel) {

        try {
            System.out.println("POSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOST");
            StudentModel answer = sal.addStudent(studentModel);

            switch ( answer.getFirstname()) {
                case "empty":
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity("{\"Fill in all details please\"}").build();
                case "duplicate":
                    return Response.status(Response.Status.EXPECTATION_FAILED).entity("{\"Email already registered!\"}").build();
                default:
                    System.out.println("ENDOFPOSTENDOFPOSTENDOFPOSTENDOFPOSTENDOFPOSTENDOFPOSTENDOFPOSTENDOFPOST");
                    return Response.ok(answer).build();
            }

        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{email}")
    public Response deleteStudent(@PathParam("email") String email) {
        try {
            sal.removeStudent(email);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Student could not be found\"}").build();
        }
    }

    @PUT
    public Response updateStudent (
            @QueryParam("firstname") String firstname,
            @QueryParam("lastname") String lastname,
            @QueryParam("email") String email) {

        try{
            sal.updateStudent(firstname, lastname, email);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Check email parameter!\"}").build();
        }
    }

    @PATCH
    @Path("{email}")
    public Response updateStudentFirstname(String studentModel) {
        try{
            sal.updateStudentPartial(studentModel);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Fill in all parameters correctly!\"}").build();
        }
    }
}

