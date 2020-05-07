package dbbg2.data.users;

import dbbg2.data.users.visitorcategory.VisitorCategoryType;
import dbbg2.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.text.html.parser.Entity;

public class UserManager {

    public static User getAuthenticatedUser(String userId, String password) {
        // TODO Implement this
        return getUser(userId);
    }

    public static User getUser(String userId) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<User> q = em.createQuery("SELECT u FROM Users u WHERE u.userId=:userId",  User.class);
        return q.setParameter("userId",userId).getSingleResult();
    }

    public static Employee getEmployee(String userId) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employees e WHERE e.userId=:userId",  Employee.class);
        return q.setParameter("userId",userId).getSingleResult();
    }

    public static Visitor getVisitor(String userId) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Visitor> q = em.createQuery("SELECT v FROM Visitor v WHERE v.userId=:userId",  Visitor.class);
        return q.setParameter("userId",userId).getSingleResult();
    }

}
