package ua.com.foxminded.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration class DataSourceConfig for database
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DataSourceConfig {

    /**
     * Property - SCHEMA_SQL_FILE is path to sql file with database schema
     */
    private static final String SCHEMA_SQL_FILE = "/db/schema.sql";
    /**
     * Property - TEST_DATA_FILE is path to sql file with initial data
     */
    private static final String TEST_DATA_FILE = "/db/testData.sql";

    /**
     * Property - driver for postgres
     */
    @Value("${postgres.driver}")
    private String driverClassName;

    /**
     * Property - url for postgres
     */
    @Value("${postgres.url}")
    private String url;

    /**
     * Property - username for postgres
     */
    @Value("${postgres.userName}")
    private String userName;

    /**
     * Property - password for postgres
     */
    @Value("${postgres.password}")
    private String password;

    /**
     * Creates LocalSessionFactoryBean bean for AbstractDao methods
     * for example {@link ua.com.foxminded.dao.AbstractDao#create(Object)},
     * whose use {@link SessionFactory#openSession()}. It also sets data source: {@link #dataSource()},
     * package to scan: {@link ua.com.foxminded}, Hibernate properties: {@link #hibernateProperties()}
     *
     * @return LocalSessionFactoryBean which opens the session for Session object in AbstractDao
     * @see ua.com.foxminded.dao.AbstractDao
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ua.com.foxminded");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    /**
     * Creates DataSource bean for {@link #dataSourceInitializer(DataSource)}
     * with customized postgres properties
     *
     * @return DataSource bean for {@link #dataSourceInitializer(DataSource)}
     */
    @Bean
    public DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

    /**
     * Creates DataSourceInitializer bean which executes sql scripts in paths: {@link #SCHEMA_SQL_FILE},
     * {@link #TEST_DATA_FILE}, sets data source: {@link #dataSource()}
     *
     * @param dataSource data source with customized properties for establishing connection with database
     * @return DataSourceInitializer bean to initialize test data and work with database
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource(SCHEMA_SQL_FILE));
        resourceDatabasePopulator.addScript(new ClassPathResource(TEST_DATA_FILE));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        return dataSourceInitializer;
    }

    /**
     * Creates PlatformTransaction bean with fixed SessionFactory object for Hibernate transaction manager
     *
     * @return PlatformTransactionManager bean with fixed SessionFactory object for Hibernate transaction manager
     */
    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    /**
     * Returns properties for update entities in database and postgres dialect for Hibernate
     *
     * @return Properties for update entities in database and postgres dialect for Hibernate
     */
    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
            "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
            "hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");

        return hibernateProperties;
    }
}
