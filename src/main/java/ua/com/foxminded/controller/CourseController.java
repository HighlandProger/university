package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.service.CourseService;
import ua.com.foxminded.service.CrudService;

@Controller
@RequestMapping("/courses")
public class CourseController extends CrudController<Course> {

    private static final String ROOT_PACKAGE = "courses";
    private static final String INDEX_ENTITY_NAME = "courses";
    private static final String ENTITY_NAME = "course";
    private static final String ID = "id";
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

    @Override
    protected String getIndexEntityName() {
        return INDEX_ENTITY_NAME;
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }

    public String index(Model model) {
        return super.index(model);
    }

    public String show(@PathVariable(ID) long id, Model model) {
        return super.show(id, model);
    }

    @GetMapping("/new")
    public String newEntity(@ModelAttribute(ENTITY_NAME) Course course) {
        return super.newEntity();
    }

    @PostMapping
    public String create(@ModelAttribute(ENTITY_NAME) Course course) {
        return super.create(course);
    }

    public String edit(Model model, @PathVariable(ID) long id) {
        return super.edit(model, id);
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute(ENTITY_NAME) Course course, @PathVariable(ID) long id) {
        return super.update(course, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        return super.delete(id);
    }
}
