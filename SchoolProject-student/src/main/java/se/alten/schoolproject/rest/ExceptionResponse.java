package se.alten.schoolproject.rest;

import javax.ws.rs.core.Response;

public class ExceptionResponse {
    public static Response getResponse(Response.Status status, Exception exception){

        String responseBody = "{" +
                "\"status\":\"" + status.getStatusCode() + "\"," +
                "\"reason\":\"" + status.getReasonPhrase() + "\"," +
                "\"detail\":\"" + exception.getMessage() + "\"," +
                "}";
        return Response.status(status).header("Content-Type", "application/problem+json").entity(responseBody).build();

    }
}
