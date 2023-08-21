package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String TABLE_NAME = "users";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        Session session = null;

        String sql = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                     "(id SERIAL  PRIMARY KEY NOT NULL," +
                     " name VARCHAR(128) NOT NULL," +
                     " last_name VARCHAR(128) NOT NULL," +
                     " age INT NOT NULL)";

        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("ROLLBACK");
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        Session session = null;
        String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;

        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("ROLLBACK");
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("ROLLBACK");
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("ROLLBACK");
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
            query.from(User.class);
            userList = session.createQuery(query).getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("ROLLBACK");
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            String Hql = "DELETE FROM " + TABLE_NAME;
            transaction = session.beginTransaction();
            Query query = session.createNativeQuery(Hql);
            query.executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("ROLLBACK");
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
