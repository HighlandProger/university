package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController extends CrudController<Student> {

    private static final String ROOT_PACKAGE = "students";
    private static final String GROUPS_NAME = "groups";
    private static final String GROUP_NAME = "group";
    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @Override
    protected CrudService<Student> getCrudService() {
        return studentService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Student student) {

        model.addAttribute(ENTITY_NAME, student);
        model.addAttribute(GROUPS_NAME, groupService.getAll());
        return ROOT_PACKAGE + NEW_VIEW;
    }

    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_NAME, this.getCrudService().getById(id));
        model.addAttribute(GROUPS_NAME, groupService.getAll());
        return this.getRootPackage() + EDIT_VIEW;
    }

    @Override
    @GetMapping("/{id}")
    protected String show(@PathVariable(ID) long id, Model model) {

        Student student = studentService.getById(id);
        model.addAttribute(ENTITY_NAME, student);
        model.addAttribute(GROUP_NAME, groupService.getById(student.getGroupId()));
        return this.getRootPackage() + SHOW_VIEW;
    }
}
