package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController extends CrudController<Student> {

    private static final String ROOT_PACKAGE = "students";
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    protected CrudService<Student> getCrudService() {
        return studentService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

}
