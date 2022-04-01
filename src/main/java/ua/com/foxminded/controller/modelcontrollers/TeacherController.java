package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.dto.TeacherDTO;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController extends AbstractController<Teacher> {

    /**
     * Property - {@link #VIEW_FOLDER_NAME} name of folder with index view
     */
    private static final String VIEW_FOLDER_NAME = "teachers";
    /**
     * Property - {@link #DEPARTMENTS_ATTRIBUTE_NAME} for model attribute
     */
    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";
    /**
     * Property - {@link #teacherService} service for database
     */
    private final TeacherService teacherService;
    /**
     * Property - {@link #departmentService} service for database
     */
    private final DepartmentService departmentService;

    /**
     * Constructor {@link TeacherController} initializes {@link #teacherService},
     * {@link #teacherService}, properties using Spring
     *
     * @param teacherService    {@link TeacherService} bean
     * @param departmentService {@link DepartmentService} bean
     */
    @Autowired
    public TeacherController(TeacherService teacherService, DepartmentService departmentService) {
        this.teacherService = teacherService;
        this.departmentService = departmentService;
    }

    /**
     * Returns {@link AbstractService} with value {@link Teacher} for using inherited methods
     *
     * @return {@link AbstractService} with value {@link Teacher} for using inherited methods
     * @see AbstractController#getCrudService()
     */
    @Override
    protected AbstractService<Teacher> getCrudService() {
        return teacherService;
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
     * <p>Mapping for GET request sends two models:
     * {@link TeacherDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Department} objects in database with attribute name: {@link #DEPARTMENTS_ATTRIBUTE_NAME}
     *
     * @param model   model to send to view
     * @param teacher new {@link Teacher} object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with empty fields
     */
    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Teacher teacher) {

        TeacherDTO teacherDTO = new TeacherDTO(teacher);
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, teacherDTO);
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        return VIEW_FOLDER_NAME + EDIT_VIEW;
    }

    /**
     * Returns {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields.
     *
     * <p>Mapping for GET request sends two models:
     * {@link TeacherDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Department} objects in database with attribute name: {@link #DEPARTMENTS_ATTRIBUTE_NAME}
     *
     * @param model model to send to view
     * @param id    {@link #ID} of {@link Teacher} object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields
     */
    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        TeacherDTO teacherDTO = new TeacherDTO(teacherService.getById(id));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, teacherDTO);
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        return this.getViewFolderName() + EDIT_VIEW;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for POST request casts model to {@link TeacherDTO}
     * object. After, retrieved {@link TeacherDTO} object converts to {@link Teacher}
     * object and saves to database
     *
     * @param teacherDTO retrieved from model {@link TeacherDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see TeacherDTO#getTeacher(AbstractService)
     */
    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) TeacherDTO teacherDTO) {

        Teacher teacher = teacherDTO.getTeacher(departmentService);
        teacherService.create(teacher);
        return REDIRECT + getViewFolderName();
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for PUT request casts model to {@link TeacherDTO}
     * object. After, retrieved {@link TeacherDTO} object converts to {@link Teacher}
     * object and updates in database
     *
     * @param teacherDTO retrieved from model {@link TeacherDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see TeacherDTO#getTeacher(AbstractService)
     */
    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) TeacherDTO teacherDTO) {

        Teacher teacher = teacherDTO.getTeacher(departmentService);
        teacherService.update(teacher);
        return REDIRECT + getViewFolderName();
    }

}
