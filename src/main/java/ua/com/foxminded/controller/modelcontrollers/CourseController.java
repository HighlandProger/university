package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.CourseService;

/**
 * Controller class {@link CourseController} for model {@link Course}
 */
@Controller
@RequestMapping("/courses")
public class CourseController extends AbstractController<Course> {

    /**
     * Property - {@link #VIEW_FOLDER_NAME} name of folder with index view
     */
    private static final String VIEW_FOLDER_NAME = "courses";

    /**
     * Property - {@link #courseService} service for database
     */
    private final CourseService courseService;

    /**
     * Constructor {@link CourseController} initializes {@link #courseService} property using Spring
     *
     * @param courseService {@link CourseService} bean
     */
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Returns {@link AbstractService} with value {@link Course} for using inherited methods
     *
     * @return {@link AbstractService} with value {@link Course} for using inherited methods
     * @see AbstractController#getCrudService()
     */
    @Override
    protected AbstractService<Course> getCrudService() {
        return courseService;
    }

    /**
     * Returns {@link #VIEW_FOLDER_NAME} for using inherited methods
     *
     * @return {@link #VIEW_FOLDER_NAME} for using inherited methods
     * @see AbstractController#getViewFolderName()
     */
    @Override
    protected String getViewFolderName() {
        return VIEW_FOLDER_NAME;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for POST request casts model to {@link Course}
     * object. After, retrieved {@link Course} object saves to database
     *
     * @param course retrieved from model {@link Course} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * with all and created a new one {@link Course} objects
     * @see AbstractService#create(Object)
     */
    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) Course course) {

        courseService.create(course);
        return REDIRECT + this.getViewFolderName();
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for PUT request casts model to {@link Course}
     * object. After, retrieved {@link Course} object updates in database
     *
     * @param course retrieved from model {@link Course} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#update(Object)
     */
    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) Course course) {

        courseService.update(course);
        return REDIRECT + this.getViewFolderName();
    }
}
