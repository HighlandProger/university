package ua.com.foxminded.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentController extends CrudController<Department> {

    private static final String ROOT_PACKAGE = "departments";
    private static final String INDEX_ATTRIBUTE_NAME = "departments";
    private static final String ATTRIBUTE_NAME = "department";
    private static final String ID = "id";
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    CrudService<Department> getCrudService() {
        return departmentService;
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
    public String newEntity(@ModelAttribute(ATTRIBUTE_NAME) Department department) {
        return super.newEntity();
    }

    @Override
    @PostMapping
    public String create(@ModelAttribute(ATTRIBUTE_NAME) Department department) {
        return super.create(department);
    }

    @Override
    public String edit(Model model, @PathVariable(ID) long id) {
        return super.edit(model, id);
    }

    @Override
    @PatchMapping("/{id}")
    public String update(@ModelAttribute(ATTRIBUTE_NAME) Department department, @PathVariable(ID) long id) {
        return super.update(department, id);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(ID) long id) {
        return super.delete(id);
    }
}
