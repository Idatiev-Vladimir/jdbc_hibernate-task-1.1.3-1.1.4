package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static SessionFactory sessionFactory;

    private static void createSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting(Environment.URL, "jdbc:postgresql://localhost:5433/postgres")
                .applySetting(Environment.DRIVER, "org.postgresql.Driver")
                .applySetting(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL95Dialect")
                .applySetting(Environment.USER, "postgres")
                .applySetting(Environment.PASS, "1234")
                .applySetting(Environment.HBM2DDL_CHARSET_NAME, "none")
                .applySetting(Environment.SHOW_SQL, true)
                .applySetting(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .buildMetadata();

        sessionFactory = metadata.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;
    }


    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
