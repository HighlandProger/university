package ua.com.foxminded;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.com.foxminded.config.SpringConfig;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.service.CourseService;

public class UniversityApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        CourseService courseService = context.getBean("courseService", CourseService.class);

        Course course = new Course(2021);

        courseService.create(course);
    }
}
