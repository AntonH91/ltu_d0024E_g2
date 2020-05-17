package dbbg2.data.users.visitorcategory;

import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class VisitorCategoryManager {
    public static List<VisitorCategory> getVisitorCategories() throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();
        TypedQuery<VisitorCategory> tq = em.createQuery("SELECT vc FROM VisitorCategory vc", VisitorCategory.class);

        return tq.getResultList();
    }


}
