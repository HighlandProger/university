package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.dto.StudentDTO;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController extends AbstractController<Student> {

    /**
     * Property - {@link #VIEW_FOLDER_NAME} name of folder with index view
     */
    private static final String VIEW_FOLDER_NAME = "students";
    /**
     * Property - {@link #GROUPS_ATTRIBUTE_NAME} for model attribute
     */
    private static final String GROUPS_ATTRIBUTE_NAME = "groups";
    /**
     * Property - {@link #studentService} service for database
     */
    private final StudentService studentService;
    /**
     * Property - {@link #groupService} service for database
     */
    private final GroupService groupService;

    /**
     * Constructor {@link StudentController} initializes {@link #studentService},
     * {@link #groupService}, properties using Spring
     *
     * @param studentService {@link StudentService} bean
     * @param groupService   {@link GroupService} bean
     */
    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    /**
     * Returns {@link AbstractService} with value {@link Student} for using inherited methods
     *
     * @return {@link AbstractService} with value {@link Student} for using inherited methods
     * @see AbstractController#getCrudService()
     */
    @Override
    protected AbstractService<Student> getCrudService() {
        return studentService;
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
     * {@link StudentDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Group} objects in database with attribute name: {@link #GROUPS_ATTRIBUTE_NAME}
     *
     * @param model   model to send to view
     * @param student new {@link Student} object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with empty fields
     */
    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Student student) {

        StudentDTO studentDTO = new StudentDTO(student);
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, studentDTO);
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        return VIEW_FOLDER_NAME + EDIT_VIEW;
    }

    /**
     * Returns {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields.
     *
     * <p>Mapping for GET request sends two models:
     * {@link StudentDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Group} objects in database with attribute name: {@link #GROUPS_ATTRIBUTE_NAME}
     *
     * @param model model to send to view
     * @param id    {@link #ID} of {@link Student}object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields
     */
    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        StudentDTO studentDTO = new StudentDTO(studentService.getById(id));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, studentDTO);
        model.addAttribute(GROUPS_ATTRIBUTE_NAME, groupService.getAll());
        return this.getViewFolderName() + EDIT_VIEW;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for POST request casts model to {@link StudentDTO}
     * object. After, retrieved {@link StudentDTO} object converts to {@link Student}
     * object and saves to database
     *
     * @param studentDTO retrieved from model {@link StudentDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see StudentDTO#getStudent(AbstractService)
     */
    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) StudentDTO studentDTO) {

        Student student = studentDTO.getStudent(groupService);
        studentService.create(student);
        return REDIRECT + getViewFolderName();
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for PUT request casts model to {@link StudentDTO}
     * object. After, retrieved {@link StudentDTO} object converts to {@link Student}
     * object and updates in database
     *
     * @param studentDTO retrieved from model {@link StudentDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see StudentDTO#getStudent(AbstractService)
     */
    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) StudentDTO studentDTO) {

        Student student = studentDTO.getStudent(groupService);
        studentService.update(student);
        return REDIRECT + getViewFolderName();
    }
}
