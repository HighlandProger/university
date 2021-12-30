package ua.com.foxminded.util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceFactory {

    private static DataSourceFactory instance;

    private DriverManagerDataSource dataSource;

    private DataSourceFactory(){
    }

    public static DataSourceFactory getInstance(){
        if (instance == null){
            instance = new DataSourceFactory();
        }
        return instance;
    }

    public DriverManagerDataSource initDataSource(){

        if (dataSource!=null){
            return dataSource;
        }

        DriverManagerDataSource driverManager = new DriverManagerDataSource();

        driverManager.setDriverClassName("org.postgresql.Driver");
        driverManager.setUrl("jdbc:postgresql://localhost:5432/university_db");
        driverManager.setUsername("postgres");
        driverManager.setPassword("postgres");

        return driverManager;
    }
}
