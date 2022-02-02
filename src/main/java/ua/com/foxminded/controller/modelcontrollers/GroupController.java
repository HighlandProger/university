package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.service.CourseService;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController extends CrudController<Group> {

    private static final String ROOT_PACKAGE = "groups";
    private static final String DEPARTMENTS_NAME = "departments";
    private static final String DEPARTMENT_NAME = "department";
    private static final String COURSES_NAME = "courses";
    private static final String COURSE_NAME = "course";
    private final GroupService groupService;
    private final DepartmentService departmentService;
    private final CourseService courseService;

    @Autowired
    public GroupController(GroupService groupService, DepartmentService departmentService, CourseService courseService) {
        this.groupService = groupService;
        this.departmentService = departmentService;
        this.courseService = courseService;
    }

    @Override
    protected CrudService<Group> getCrudService() {
        return groupService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Group group) {

        model.addAttribute(ENTITY_NAME, group);
        model.addAttribute(DEPARTMENTS_NAME, departmentService.getAll());
        model.addAttribute(COURSES_NAME, courseService.getAll());
        return ROOT_PACKAGE + NEW_VIEW;
    }

    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_NAME, this.getCrudService().getById(id));
        model.addAttribute(DEPARTMENTS_NAME, departmentService.getAll());
        model.addAttribute(COURSES_NAME, courseService.getAll());
        return this.getRootPackage() + EDIT_VIEW;
    }

    @Override
    @GetMapping("/{id}")
    protected String show(@PathVariable(ID) long id, Model model) {

        Group group = groupService.getById(id);
        model.addAttribute(ENTITY_NAME, group);
        model.addAttribute(DEPARTMENT_NAME, departmentService.getById(group.getDepartmentId()));
        model.addAttribute(COURSE_NAME, courseService.getById(group.getCourseId()));
        return ROOT_PACKAGE + SHOW_VIEW;
    }
}
