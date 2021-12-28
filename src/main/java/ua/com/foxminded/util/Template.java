package ua.com.foxminded.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Template {

    private Template(){}

    private static DriverManagerDataSource getDriverManager(){

        DriverManagerDataSource driverManager = new DriverManagerDataSource();

        driverManager.setDriverClassName("org.postgresql.Driver");
        driverManager.setUrl("jdbc:postgresql://localhost:5432/university_db");
        driverManager.setUsername("postgres");
        driverManager.setPassword("postgres");

        return driverManager;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDriverManager());
    }
}
