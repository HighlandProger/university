package ua.com.foxminded.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Configuration class SpringWebApplicationInitializer for Dispatcher Servlet
 */
public class SpringWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * Returns configuration classes for Dispatcher Servlet
     *
     * @return configuration classes for Dispatcher Servlet
     * @see SpringWebConfig
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringWebConfig.class};
    }

    /**
     * Returns array of strings, which sets symbols for Dispatcher Servlet mapping
     *
     * @return array of strings, which sets symbols for Dispatcher Servlet mapping
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * Returns array of filters for hidden http methods
     *
     * @return array of filters for hidden http methods
     */
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new HiddenHttpMethodFilter()};
    }

}
