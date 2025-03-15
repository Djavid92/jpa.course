package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USERNAME = "jpauser";
    private static final String PASSWORD = "jpapwd";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/test_db");
            settings.put("hibernate.connection.username", "jpauser");
            settings.put("hibernate.connection.password", "jpapwd");
            settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            settings.put("hibernate.show_sql", "true");
            settings.put("hibernate.hbm2ddl.auto", "none");

            Configuration configuration = new Configuration();
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка инициализации SessionFactory");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
