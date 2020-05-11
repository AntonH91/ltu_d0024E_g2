package dbbg2.data.users;

import dbbg2.utils.persistence.JpaPersistence;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserManager {

    /**
     * Gets a user from the database and marks them as authenticated
     * @param userId The userID to retrieve
     * @param password The user's password
     * @return The retrieved user
     * @throws NoResultException If no user can be found with the given credentials
     */
    public static User getAuthenticatedUser(String userId, String password) throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();

        TypedQuery<User> q = em.createQuery("SELECT u FROM Users u WHERE u.userId=:userId AND u.password=:password", User.class);
        q.setParameter("userId",userId);
        q.setParameter("password", DigestUtils.md5Hex(password));

        return q.getSingleResult();
    }

    /**
     * Retrieves a generic user from the table
     * @param userId The userId to retrieve
     * @return The requested user
     * @throws NoResultException Thrown when the user cannot be found
     */
    public static User getUser(String userId) throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<User> q = em.createQuery("SELECT u FROM Users u WHERE u.userId=:userId",  User.class);
        return q.setParameter("userId",userId).getSingleResult();
    }

    /**
     * Retrieves an employee from the database
     * @param userId The userid of the employee to retrieve
     * @return The found employee
     * @throws NoResultException When the employee with the given userId cannot be found
     */
    public static Employee getEmployee(String userId) throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employees e WHERE e.userId=:userId",  Employee.class);
        return q.setParameter("userId",userId).getSingleResult();
    }

    public static Visitor getVisitor(String userId) {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<Visitor> q = em.createQuery("SELECT v FROM Visitor v WHERE v.userId=:userId",  Visitor.class);
        return q.setParameter("userId",userId).getSingleResult();
    }

    public static List<User> getUsers() {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<User> q = em.createQuery("SELECT u FROM Users u", User.class);

        return q.getResultList();
    }

    public static List<User> getUsers(String userID, String firstName, String lastName, String email){
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<User> q = em.createQuery("SELECT u FROM Users u " +
                "WHERE (u.userId = :userId or :userId = '') " +
                "AND (u.firstName = :firstName or :firstName = '') " +
                "AND (u.lastName = :lastName or :lastName = '') " +
                "AND (u.email = :email or :email = '') ", User.class);

        q.setParameter("userId", userID);
        q.setParameter("firstName", firstName);
        q.setParameter("lastName", lastName);
        q.setParameter("email", email);

        return q.getResultList();
    }


}
