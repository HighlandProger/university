package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController extends CrudController<Teacher> {

    private static final String ROOT_PACKAGE = "teachers";
    private static final String DEPARTMENTS_NAME = "departments";
    private static final String DEPARTMENT_NAME = "department";
    private final TeacherService teacherService;
    private final DepartmentService departmentService;

    @Autowired
    public TeacherController(TeacherService teacherService, DepartmentService departmentService) {
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    @Override
    protected CrudService<Teacher> getCrudService() {
        return teacherService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Teacher teacher){

        model.addAttribute(ENTITY_NAME, teacher);
        model.addAttribute(DEPARTMENTS_NAME, departmentService.getAll());
        return ROOT_PACKAGE + NEW_VIEW;
    }

    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_NAME, this.getCrudService().getById(id));
        model.addAttribute(DEPARTMENTS_NAME, departmentService.getAll());
        return this.getRootPackage() + EDIT_VIEW;
    }

    @Override
    @GetMapping("/{id}")
    protected String show(@PathVariable(ID) long id, Model model) {

        Teacher teacher = teacherService.getById(id);
        model.addAttribute(ENTITY_NAME, teacher);
        model.addAttribute(DEPARTMENT_NAME, departmentService.getById(teacher.getDepartmentId()));
        return this.getRootPackage() + SHOW_VIEW;
    }
}
