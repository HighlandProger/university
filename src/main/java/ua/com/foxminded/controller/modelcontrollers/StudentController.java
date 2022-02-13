package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.dto.StudentDTO;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController extends CrudController<Student> {

    private static final String ROOT_PACKAGE = "students";
    private static final String GROUPS_ATTRIBUTE_NAME = "groups";
    private static final String STUDENT_DTOS_ATTRIBUTE_NAME = "studentDTOS";
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

    private List<StudentDTO> getStudentDTOS(){
        return studentService.getAll().stream()
            .map(el -> new StudentDTO(
                el,
                groupService.getById(el.getGroupId())))
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping
    protected String index(Model model) {

        model.addAttribute(STUDENT_DTOS_ATTRIBUTE_NAME, getStudentDTOS());
        return super.index(model);
    }

    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Student student) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, student);
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        return ROOT_PACKAGE + EDIT_VIEW;
    }

    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, this.getCrudService().getById(id));
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        return this.getRootPackage() + EDIT_VIEW;
    }
}
