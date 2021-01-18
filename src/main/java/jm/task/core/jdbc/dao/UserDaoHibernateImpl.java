package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Session session;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        SessionFactory factory = Util.createSessionFactory();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (ID Bigint NOT NULL AUTO_INCREMENT," +
                    " NAME varchar (255), LASTNAME varchar (255), AGE int (3), PRIMARY KEY (ID))")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory factory = Util.createSessionFactory();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory factory = Util.createSessionFactory();
        User user = new User(name, lastName, age);
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User c именем - " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory factory = Util.createSessionFactory();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User where id = " + id)
                    .executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory factory = Util.createSessionFactory();
        List<User> userList = new ArrayList<>();

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            userList = session.createQuery("from User").list();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory factory = Util.createSessionFactory();
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }
}
