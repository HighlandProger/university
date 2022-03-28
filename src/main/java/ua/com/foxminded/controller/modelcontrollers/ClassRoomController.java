package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.ClassRoomService;

/**
 * Controller class {@link ClassRoomController} for model {@link ClassRoom}
 */
@Controller
@RequestMapping("/classrooms")
public class ClassRoomController extends AbstractController<ClassRoom> {

    /**
     * Property - {@link #VIEW_FOLDER_NAME} name of folder with index view
     */
    private static final String VIEW_FOLDER_NAME = "classrooms";
    /**
     * Property - {@link #classRoomService} service for database
     */
    private final ClassRoomService classRoomService;

    /**
     * Constructor {@link ClassRoomController} initializes {@link #classRoomService} property using Spring
     *
     * @param classRoomService {@link ClassRoomService} bean
     */
    @Autowired
    public ClassRoomController(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    /**
     * Returns {@link AbstractService} with value {@link ClassRoom} for using inherited methods
     *
     * @return {@link AbstractService} with value {@link ClassRoom} for using inherited methods
     * @see AbstractController#getCrudService()
     */
    @Override
    protected AbstractService<ClassRoom> getCrudService() {
        return classRoomService;
    }

    /**
     * Returns {@link #VIEW_FOLDER_NAME} for using inherited methods
     *
     * @return {@link #VIEW_FOLDER_NAME} for using inherited methods
     * @see AbstractController#getViewFolderName()
     */
    @Override
    protected String getViewFolderName() {
        return VIEW_FOLDER_NAME;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for POST request casts model to {@link ClassRoom}
     * object. After, retrieved {@link ClassRoom} object saves to database
     *
     * @param classRoom retrieved from model {@link ClassRoom} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * with all and created a new one {@link ClassRoom} objects
     * @see AbstractService#create(Object)
     */
    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) ClassRoom classRoom) {

        classRoomService.create(classRoom);
        return REDIRECT + this.getViewFolderName();
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for PUT request casts model to {@link ClassRoom}
     * object. After, retrieved {@link ClassRoom} object updates in database
     *
     * @param classRoom retrieved from model {@link ClassRoom} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#update(Object)
     */
    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) ClassRoom classRoom) {

        classRoomService.update(classRoom);
        return REDIRECT + this.getViewFolderName();
    }

}
