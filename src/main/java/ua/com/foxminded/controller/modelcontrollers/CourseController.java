package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController extends AbstractController<Course> {

    private static final String VIEW_FOLDER_NAME = "courses";
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected AbstractService<Course> getCrudService() {
        return courseService;
    }

    @Override
    protected String getViewFolderName() {
        return VIEW_FOLDER_NAME;
    }

}
