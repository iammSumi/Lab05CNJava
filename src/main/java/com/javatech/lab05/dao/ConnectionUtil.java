package com.javatech.lab05.dao;

import com.javatech.lab05.entity.Product;
import com.javatech.lab05.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static final String DB_URL = "jdbc:mysql://localhost:3306/java_tech_lab05";

    private static SessionFactory sessionFactory;
    private Session session;
    private static ConnectionUtil instance;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", DB_URL);
            properties.setProperty("hibernate.connection.username", "root");
            properties.setProperty("hibernate.connection.password", "123456");
            properties.setProperty("hibernate.connection.driver_class", JDBC_DRIVER);
//        MySql8Dialect
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "true");
            properties.setProperty("hibernate.hbm2ddl.auto", "update");

            sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Product.class)
                    .addProperties(properties)
                    .buildSessionFactory();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionUtil() {
        this.session = sessionFactory.openSession();
    }

    public static ConnectionUtil getInstance() {
        if (instance == null) {
            instance = new ConnectionUtil();
        }
        return instance;
    }

    public Session getSession() {
        return session;
    }

    public void close() {
        session.close();
        sessionFactory.close();
    }
}
