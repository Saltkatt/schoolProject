package se.alten.schoolproject.rest;


import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/subject")
public class SubjectController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSubjects() {
        try {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            List<SubjectModel> subject = sal.listAllSubjects();
            subject.forEach(t -> {
                System.out.println(t.getTitle() + " from List forEach in Controller");
            });
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            return Response.ok(subject).build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{title}")
    public Response listSubjectByTitle(@PathParam("title") String title) {
        try {
            SubjectModel findSubject = sal.getSubjectByName(title);
            return Response.ok(findSubject).build();
        } catch (Exception e) {
            e.getMessage();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubject(String subject) {
        try {
            SubjectModel subjectModel = sal.addSubject(subject);
            return Response.ok(subjectModel).build();
        } catch (Exception e ) {
            return Response.status(404).build();
        }
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{title}")
    public Response updateJoinTable(@PathParam("title") String title, String studentEmail){

        try{
           sal.addStudentToSubject(title, studentEmail);
            return Response.ok().build();
        }catch (BadRequestException e){
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Subject could not be found\"}").build();

        }

    }

    @DELETE
    @Path("{title}")
    public Response deleteSubject(@PathParam("title") String title){

        try {
            sal.removeSubject(title);
            return Response.ok().status(Response.Status.NO_CONTENT).build();
        }catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity("{\"Subject could not be found\"}").build();
        }
    }
}
