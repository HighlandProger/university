package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController extends CrudController<Teacher> {

    private static final String ROOT_PACKAGE = "teachers";
    private static final String INDEX_ATTRIBUTE_NAME = "teachers";
    private static final String ATTRIBUTE_NAME = "teacher";
    private static final String ID = "id";
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    protected CrudService<Teacher> getCrudService(){
        return teacherService;
    }

    @Override
    protected String getRootPackage(){
        return ROOT_PACKAGE;
    }

    @Override
    protected String getIndexEntityName(){
        return INDEX_ATTRIBUTE_NAME;
    }

    @Override
    protected String getEntityName(){
        return ATTRIBUTE_NAME;
    }

    public String index(Model model) {
        return super.index(model);
    }

    public String show(@PathVariable(ID) long id, Model model) {
        return super.show(id, model);
    }

    @GetMapping("/new")
    public String newEntity(@ModelAttribute(ATTRIBUTE_NAME) Teacher teacher) {
        return super.newEntity();
    }

    @PostMapping
    public String create(@ModelAttribute(ATTRIBUTE_NAME) Teacher teacher) {
        return super.create(teacher);
    }

    public String edit(Model model, @PathVariable(ID) long id) {
        return super.edit(model, id);
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute(ATTRIBUTE_NAME) Teacher teacher, @PathVariable(ID) long id) {
        return super.update(teacher, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        return super.delete(id);
    }
}
