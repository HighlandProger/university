package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.service.CourseService;
import ua.com.foxminded.service.CrudService;

@Controller
@RequestMapping("/courses")
public class CourseController extends CrudController<Course> {

    private static final String ROOT_PACKAGE = "courses";
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected CrudService<Course> getCrudService() {
        return courseService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

}
