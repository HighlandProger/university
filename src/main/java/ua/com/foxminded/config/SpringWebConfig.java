package ua.com.foxminded.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * Configuration class SpringWebConfig for web
 */
@Configuration
@ComponentScan("ua.com.foxminded")
@EnableWebMvc
public class SpringWebConfig implements WebMvcConfigurer {

    /**
     * Property - application context
     */
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringWebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Creates SpringResourceTemplateResolver bean with configured {@link #applicationContext},
     * prefix and suffix for views
     *
     * @return SpringResourceTemplateResolver bean with configured {@link #applicationContext},
     * prefix and suffix for views
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");

        return templateResolver;
    }

    /**
     * Creates SpringTemplateEngine bean with configured dialect, {@link #templateResolver()}
     * and EnableSpringELCompiler
     *
     * @return SpringTemplateEngine bean with configured dialect, {@link #templateResolver()}
     * and EnableSpringELCompiler
     */
    @Bean
    public SpringTemplateEngine templateEngine() {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);

        return templateEngine;
    }

    /**
     * Configures resolver in views
     *
     * @param registry ViewResolverRegistry with set viewResolver
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/static/**")) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
    }
}
