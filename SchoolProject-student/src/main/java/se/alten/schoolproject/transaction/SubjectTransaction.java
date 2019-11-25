package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Subject;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

@Stateless
@Default
public class SubjectTransaction implements SubjectTransactionAccess{

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllSubjects() {
        String select = "SELECT s FROM Subject s";
        Query query = entityManager.createQuery(select);
        return query.getResultList();
    }

    @Override
    public Subject addSubject(Subject subject) {
        try {
            entityManager.persist(subject);
            entityManager.flush();
            return subject;
        } catch ( PersistenceException pe ) {
            subject.setTitle("duplicate");
            return subject;
        }
    }

    @Override
    public List<Subject> getSubjectByName(List<String> subject) {

        String queryStr = "SELECT sub FROM Subject sub WHERE sub.title IN :subject";
        TypedQuery<Subject> query = entityManager.createQuery(queryStr, Subject.class);
        query.setParameter("subject", subject);

        return query.getResultList();
    }

   /* @Override
    public void updateSubjectPartial(Subject subject) {
        String selectStudentToUpdate = "SELECT s FROM Subject s WHERE s.title = :title";
        String updateStudent = "UPDATE Subject SET firstname = :studentFirstname WHERE email = :email";
        Student studentFound = (Student)entityManager.createQuery(selectStudentToUpdate)
                .setParameter("email", student.getEmail()).getSingleResult();

        Query query = entityManager.createQuery(updateFirstName);
        query.setParameter("studentFirstname", student.getFirstname())
                .setParameter("email", studentFound.getEmail())
                .executeUpdate();
    }*/
}
