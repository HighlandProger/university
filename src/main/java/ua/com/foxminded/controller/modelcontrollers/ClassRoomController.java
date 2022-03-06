package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.ClassRoomService;

@Controller
@RequestMapping("/classrooms")
public class ClassRoomController extends AbstractController<ClassRoom> {

    private static final String VIEW_FOLDER_NAME = "classrooms";
    private final ClassRoomService classRoomService;

    @Autowired
    public ClassRoomController(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @Override
    protected AbstractService<ClassRoom> getCrudService() {
        return classRoomService;
    }

    @Override
    protected String getViewFolderName() {
        return VIEW_FOLDER_NAME;
    }

}
