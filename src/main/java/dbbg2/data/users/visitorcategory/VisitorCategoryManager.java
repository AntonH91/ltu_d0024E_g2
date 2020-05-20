package dbbg2.data.users.visitorcategory;

import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class VisitorCategoryManager {
    /**
     * Get all types of visitor categories recorded in the database
     *
     * @return A list of all visitor categories that have been recorded.
     * @throws NoResultException Thrown if there are no visitor categories in the database.
     */
    public static List<VisitorCategory> getVisitorCategories() throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<VisitorCategory> tq = em.createQuery("SELECT vc FROM VisitorCategory vc", VisitorCategory.class);

        return tq.getResultList();
    }


    /**
     * Returns the proposed default Visitor Category for self-registrations.
     * Selection is based on the earliest created VisitorCategory, in case several defaults are selected.
     *
     * @return The proposed default visitor category.
     * @throws NoResultException In case there are no default categories selected.
     */
    public static VisitorCategory getDefaultCategory() throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<VisitorCategory> tq = em.createQuery(
                "SELECT vc " +
                        "FROM VisitorCategory vc " +
                        "WHERE vc.defaultNewUser=true", VisitorCategory.class);

        return tq.getResultList().get(0);

    }

}
