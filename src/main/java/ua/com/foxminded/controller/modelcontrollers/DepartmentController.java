package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentController extends CrudController<Department> {

    private static final String ROOT_PACKAGE = "departments";
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    protected CrudService<Department> getCrudService() {
        return departmentService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

}
