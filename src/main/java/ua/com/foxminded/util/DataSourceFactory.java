package ua.com.foxminded.util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceFactory {

    private static DataSourceFactory instance;

    private DriverManagerDataSource dataSource;

    private DataSourceFactory() {
    }

    public static DataSourceFactory getInstance() {
        if (instance == null) {
            instance = new DataSourceFactory();
        }
        return instance;
    }

    public DriverManagerDataSource initDataSource() {

        if (this.dataSource != null) {
            return this.dataSource;
        }

        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/university_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }
}
