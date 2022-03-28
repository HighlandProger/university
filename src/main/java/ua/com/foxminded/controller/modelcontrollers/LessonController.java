package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.dto.LessonDTO;
import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.*;

/**
 * Controller class {@link LessonController} for model {@link Lesson}
 */
@Controller
@RequestMapping("/lessons")
public class LessonController extends AbstractController<Lesson> {

    /**
     * Property - {@link #VIEW_FOLDER_NAME} name of folder with index view
     */
    private static final String VIEW_FOLDER_NAME = "lessons";
    /**
     * Property - {@link #GROUPS_ATTRIBUTE_NAME} for model attribute
     */
    private static final String GROUPS_ATTRIBUTE_NAME = "groups";
    /**
     * Property - {@link #TEACHERS_ATTRIBUTE_NAME} for model attribute
     */
    private static final String TEACHERS_ATTRIBUTE_NAME = "teachers";
    /**
     * Property - {@link #CLASSROOMS_ATTRIBUTE_NAME} for model attribute
     */
    private static final String CLASSROOMS_ATTRIBUTE_NAME = "classrooms";

    /**
     * Property - {@link #lessonService} service for database
     */
    private final LessonService lessonService;
    /**
     * Property - {@link #groupService} service for database
     */
    private final GroupService groupService;
    /**
     * Property - {@link #teacherService} service for database
     */
    private final TeacherService teacherService;
    /**
     * Property - {@link #classRoomService} service for database
     */
    private final ClassRoomService classRoomService;

    /**
     * Constructor {@link LessonController} initializes {@link #lessonService}, {@link #groupService},
     * {@link #teacherService}, {@link #classRoomService} properties using Spring
     *
     * @param lessonService    {@link LessonService} bean
     * @param groupService     {@link GroupService} bean
     * @param teacherService   {@link TeacherService} bean
     * @param classRoomService {@link ClassRoomService} bean
     */
    @Autowired
    public LessonController(LessonService lessonService, GroupService groupService, TeacherService teacherService, ClassRoomService classRoomService) {
        this.lessonService = lessonService;
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.classRoomService = classRoomService;
    }

    /**
     * Returns {@link AbstractService} with value {@link Lesson} for using inherited methods
     *
     * @return {@link AbstractService} with value {@link Lesson} for using inherited methods
     * @see AbstractController#getCrudService()
     */
    @Override
    protected AbstractService<Lesson> getCrudService() {
        return lessonService;
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
     * Returns {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with empty fields.
     *
     * <p>Mapping for GET request sends four models:
     * {@link LessonDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Group} objects in database with attribute name: {@link #GROUPS_ATTRIBUTE_NAME},
     * list of {@link Teacher} objects in database with attribute name: {@link #TEACHERS_ATTRIBUTE_NAME}
     * list of {@link ClassRoom} objects in database with attribute name: {@link #CLASSROOMS_ATTRIBUTE_NAME}
     *
     * @param model  model to send to view
     * @param lesson new {@link Lesson} object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with empty fields
     */
    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Lesson lesson) {

        LessonDTO lessonDTO = new LessonDTO(lesson);
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lessonDTO);
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        model.addAttribute(TEACHERS_ATTRIBUTE_NAME, teacherService.getAll());
        model.addAttribute(CLASSROOMS_ATTRIBUTE_NAME, classRoomService.getAll());
        return VIEW_FOLDER_NAME + EDIT_VIEW;
    }

    /**
     * Returns {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields.
     *
     * <p>Mapping for GET request sends four models:
     * {@link LessonDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Group} objects in database with attribute name: {@link #GROUPS_ATTRIBUTE_NAME},
     * list of {@link Teacher} objects in database with attribute name: {@link #TEACHERS_ATTRIBUTE_NAME}
     * list of {@link ClassRoom} objects in database with attribute name: {@link #CLASSROOMS_ATTRIBUTE_NAME}
     *
     * @param model model to send to view
     * @param id    {@link #ID} of {@link Lesson}object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields
     */
    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        LessonDTO lessonDTO = new LessonDTO(lessonService.getById(id));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, lessonDTO);
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        model.addAttribute(TEACHERS_ATTRIBUTE_NAME, teacherService.getAll());
        model.addAttribute(CLASSROOMS_ATTRIBUTE_NAME, classRoomService.getAll());
        return this.getViewFolderName() + EDIT_VIEW;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for POST request casts model to {@link LessonDTO}
     * object. After, retrieved {@link LessonDTO} object converts to {@link Lesson}
     * object and saves to database
     *
     * @param lessonDTO retrieved from model {@link LessonDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see LessonDTO#getLesson(AbstractService, AbstractService, AbstractService)
     */
    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) LessonDTO lessonDTO) {

        Lesson lesson = lessonDTO.getLesson(groupService, teacherService, classRoomService);
        lessonService.create(lesson);
        return REDIRECT + getViewFolderName();
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for PUT request casts model to {@link LessonDTO}
     * object. After, retrieved {@link LessonDTO} object converts to {@link Lesson}
     * object and updates in database
     *
     * @param lessonDTO retrieved from model {@link LessonDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see LessonDTO#getLesson(AbstractService, AbstractService, AbstractService)
     */
    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) LessonDTO lessonDTO) {

        Lesson lesson = lessonDTO.getLesson(groupService, teacherService, classRoomService);
        lessonService.update(lesson);
        return REDIRECT + getViewFolderName();
    }
}
