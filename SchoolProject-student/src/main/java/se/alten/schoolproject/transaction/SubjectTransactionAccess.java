package se.alten.schoolproject.transaction;


import se.alten.schoolproject.entity.Subject;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {
    List listAllSubjects();
    Subject addSubject(Subject subject);
    Subject listSubjectsByTitle(String title);
    void removeSubject(String title);
    //List listSubjectsByTitle(String title);
}
