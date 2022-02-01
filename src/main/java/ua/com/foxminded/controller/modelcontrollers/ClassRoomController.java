package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.service.ClassRoomService;
import ua.com.foxminded.service.CrudService;

@Controller
@RequestMapping("/classrooms")
public class ClassRoomController extends CrudController<ClassRoom> {

    private static final String ROOT_PACKAGE = "classrooms";
    private final ClassRoomService classRoomService;

    @Autowired
    public ClassRoomController(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @Override
    protected CrudService<ClassRoom> getCrudService() {
        return classRoomService;
    }

    @Override
    protected String getRootPackage() {
        return ROOT_PACKAGE;
    }

}
