package com.javatech.lab05.service;

import com.javatech.lab05.dao.ConnectionUtil;
import com.javatech.lab05.entity.User;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class UserService implements Repository<User, Integer> {
    private static final Session session = ConnectionUtil.getInstance().getSession();

    public User findByUserNameAndPassword(String username, String password) {
        session.beginTransaction();

        try {
            User user = session.createQuery("from User where username = :username and password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }
    @Override
    public User save(User entity) {
        try {
            session.beginTransaction();
            User user = session.createQuery("from User where username = :username", User.class)
                    .setParameter("username", entity.getUsername())
                    .getSingleResultOrNull();

            if (user == null) {
                System.out.println("Username and password are not exist");
                User result = (User) session.save(entity);
                session.getTransaction().commit();
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public User findById(Integer integer) {
        return session.createQuery("from User where id = :id", User.class)
                .setParameter("id", integer)
                .getSingleResult();
    }

    @Override
    public Iterable<User> findAll() {
        return session.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void delete(User entity) {
        session.beginTransaction();
        try {
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void deleteById(Integer integer) {
        session.beginTransaction();
        try {
            session.createQuery("delete from User where id = :id")
                    .setParameter("id", integer)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public User update(User entity) {
        return null;
    }
}
