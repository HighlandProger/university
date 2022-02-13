package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.dto.TeacherDTO;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teachers")
public class TeacherController extends CrudController<Teacher> {

    private static final String ROOT_PACKAGE = "teachers";
    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";
    private static final String TEACHER_DTOS_ATTRIBUTE_NAME = "teacherDTOS";
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

    private List<TeacherDTO> getTeacherDTOS(){
        return teacherService.getAll().stream()
            .map(el -> new TeacherDTO(
                el,
                departmentService.getById(el.getDepartmentId())))
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping
    protected String index(Model model) {

        model.addAttribute(TEACHER_DTOS_ATTRIBUTE_NAME, getTeacherDTOS());
        return super.index(model);
    }

    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Teacher teacher) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, teacher);
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        return ROOT_PACKAGE + EDIT_VIEW;
    }

    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        model.addAttribute(ENTITY_ATTRIBUTE_NAME, this.getCrudService().getById(id));
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        return this.getRootPackage() + EDIT_VIEW;
    }
}
