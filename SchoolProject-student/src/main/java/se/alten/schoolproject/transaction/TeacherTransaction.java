package se.alten.schoolproject.transaction;


import org.jboss.logging.Logger;
import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Default
public class TeacherTransaction implements TeacherTransactionAccess{

    public static final Logger logger = Logger.getLogger(
            TeacherTransaction.class.getName());

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllTeachers() {
        String select = "SELECT t from Teacher t";
        Query query = entityManager.createQuery(select);
        return query.getResultList();
    }

    @Override
    public Teacher addTeacher(Teacher teacherToAdd) {
        try {
            entityManager.persist(teacherToAdd);
            entityManager.flush();
            return teacherToAdd;
        } catch ( PersistenceException pe ) {
            teacherToAdd.setFirstname("duplicate");
            return teacherToAdd;
        }
    }

    @Override
    public void removeTeacher(String teacher) {
        String deleteTeacher = "DELETE FROM Teacher t WHERE t.email = :email";
        Query query = entityManager.createQuery(deleteTeacher);

        query.setParameter("email", teacher)
                .executeUpdate();
    }


}
