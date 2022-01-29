package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.CrudService;
import ua.com.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController extends CrudController<Teacher> {

    private static final String ROOT_PACKAGE = "teachers";
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    protected CrudService<Teacher> getCrudService() {
        return teacherService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }
}
