package ua.com.foxminded.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan("ua.com.foxminded.dao")
public class SpringDaoTestConfig {


}
