package ua.com.foxminded.util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Template extends DriverManagerDataSource {

    private Template(){}

    public static DriverManagerDataSource getDataSource(){

        DriverManagerDataSource driverManager = new DriverManagerDataSource();

        driverManager.setDriverClassName("org.postgresql.Driver");
        driverManager.setUrl("jdbc:postgresql://localhost:5432/university_db");
        driverManager.setUsername("postgres");
        driverManager.setPassword("postgres");

        return driverManager;
    }

}