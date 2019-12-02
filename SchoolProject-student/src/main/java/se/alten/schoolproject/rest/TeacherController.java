package se.alten.schoolproject.rest;


import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/teacher")
public class TeacherController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allTeachers() {

        try {
            List teachers = sal.listAllTeachers();
            return Response.ok(teachers).build();
        }

        catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(String newTeacher) {

        try {
            System.out.println("POSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOSTPOST");
            TeacherModel answer = sal.addTeacher(newTeacher);

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
    public Response deleteTeacher(@PathParam("email") String email) {
        try {
            sal.removeTeacher(email);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Teacher could not be found\"}").build();
        }
    }



}
