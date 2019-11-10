package se.alten.schoolproject.exceptions;

import java.io.Serializable;

public class ConflictException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public ConflictException() {
        super();
    }

    public ConflictException(String msg) {
        super(msg);
    }

    public ConflictException(String msg, Exception e) {
        super(msg, e);
    }

}
