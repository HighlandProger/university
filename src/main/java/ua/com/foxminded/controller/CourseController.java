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
    private static final String INDEX_ATTRIBUTE_NAME = "courses";
    private static final String ATTRIBUTE_NAME = "course";
    private static final String ID = "id";
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    CrudService<Course> getCrudService() {
        return courseService;
    }

    @Override
    String getRootPackage() {
        return ROOT_PACKAGE;
    }

    @Override
    String getIndexAttributeName() {
        return INDEX_ATTRIBUTE_NAME;
    }

    @Override
    String getAttributeName() {
        return ATTRIBUTE_NAME;
    }

    @Override
    @GetMapping
    public String index(Model model) {
        return super.index(model);
    }

    @Override
    @GetMapping("/{id}")
    public String show(@PathVariable(ID) long id, Model model) {
        return super.show(id, model);
    }

    @GetMapping("/new")
    public String newEntity(@ModelAttribute(ATTRIBUTE_NAME) Course course) {
        return super.newEntity();
    }

    @Override
    @PostMapping
    public String create(@ModelAttribute(ATTRIBUTE_NAME) Course course) {
        return super.create(course);
    }

    @Override
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable(ID) long id) {
        return super.edit(model, id);
    }

    @Override
    @PatchMapping("/{id}")
    public String update(@ModelAttribute(ATTRIBUTE_NAME) Course course, @PathVariable(ID) long id) {
        return super.update(course, id);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        return super.delete(id);
    }
}
