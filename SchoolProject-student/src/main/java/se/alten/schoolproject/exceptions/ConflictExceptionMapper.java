package se.alten.schoolproject.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {
    @Override
    public Response toResponse(ConflictException e) {
        return Response.status(Response.Status.CONFLICT).entity("Check current state of target resource!" + e.getMessage()).build();
    }
}
