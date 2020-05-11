package dbbg2.data.loans;

import dbbg2.utils.persistence.JpaPersistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class LoanManager {


    public static List<Loan> getLoansFromUser(String userId) throws NoResultException {
        EntityManager em = JpaPersistence.getEntityManager();

        TypedQuery<Loan> lq = em.createQuery("SELECT l " +
                                                    "FROM Loan l " +
                                                    "INNER JOIN Visitor v ON l.client = v " +
                                                    "WHERE v.userId=:userId", Loan.class);

        List<Loan>  loanList = lq.setParameter("userId",userId).getResultList();

        return loanList;
    }





}
