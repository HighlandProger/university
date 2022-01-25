package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController extends CrudController<Student> {

    private static final String ROOT_PACKAGE = "students";
    private static final String INDEX_ATTRIBUTE_NAME = "students";
    private static final String ATTRIBUTE_NAME = "student";
    private static final String ID = "id";
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    CrudService<Student> getCrudService() {
        return studentService;
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
    public String index(Model model) {
        return super.index(model);
    }

    @Override
    public String show(@PathVariable(ID) long id, Model model) {
        return super.show(id, model);
    }

    @GetMapping("/new")
    public String newEntity(@ModelAttribute(ATTRIBUTE_NAME) Student student) {
        return super.newEntity();
    }

    @Override
    @PostMapping
    public String create(@ModelAttribute(ATTRIBUTE_NAME) Student student) {
        return super.create(student);
    }

    @Override
    public String edit(Model model, @PathVariable(ID) long id) {
        return super.edit(model, id);
    }

    @Override
    @PatchMapping("/{id}")
    public String update(@ModelAttribute(ATTRIBUTE_NAME) Student student, @PathVariable(ID) long id) {
        return super.update(student, id);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        return super.delete(id);
    }
}
