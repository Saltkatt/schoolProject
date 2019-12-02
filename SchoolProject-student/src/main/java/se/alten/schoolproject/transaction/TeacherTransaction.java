package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.List;

@Stateless
@Default
public class TeacherTransaction implements TeacherTransactionAccess {

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllTeachers() {
        String select = "SELECT t from Teacher t";
        Query query = entityManager.createQuery(select);
        return query.getResultList();
    }

    @Override
    public Teacher findTeacherByEmail(String email) {
        String queryStr = "SELECT t FROM Teacher t WHERE t.email = :email";
        TypedQuery<Teacher> query = entityManager.createQuery(queryStr, Teacher.class);
        query.setParameter("email", email);
        System.out.println("::::::::::QUERY:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(query.getSingleResult());
        System.out.println("::::::::::QUERY:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

        return query.getSingleResult();
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

  /*  @Override
    public void updateTeacher(String firstname, String lastname, String email) {
        String queryStr = "UPDATE teacher SET firstname = :firstname, lastname = :lastname WHERE email = :email";
        Query updateQuery = entityManager.createNativeQuery(queryStr, Teacher.class);
        updateQuery.setParameter("firstname", firstname)
                .setParameter("lastname", lastname)
                .setParameter("email", email)
                .executeUpdate();
    }*/


}
