package ua.com.foxminded.controller.modelcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.dto.GroupDTO;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.service.CourseService;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.GroupService;

/**
 * Controller class {@link GroupController} for model {@link Group}
 */
@Controller
@RequestMapping("/groups")
public class GroupController extends AbstractController<Group> {

    /**
     * Property - {@link #VIEW_FOLDER_NAME} of folder with index view
     */
    private static final String VIEW_FOLDER_NAME = "groups";
    /**
     * Property - {@link #DEPARTMENTS_ATTRIBUTE_NAME} for model attribute
     */
    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";
    /**
     * Property - {@link #COURSES_ATTRIBUTE_NAME} for model attribute
     */
    private static final String COURSES_ATTRIBUTE_NAME = "courses";
    /**
     * Property - {@link #groupService} service for database
     */
    private final GroupService groupService;
    /**
     * Property - {@link #departmentService} service for database
     */
    private final DepartmentService departmentService;
    /**
     * Property - {@link #courseService} service for database
     */
    private final CourseService courseService;

    /**
     * Constructor {@link GroupController} initializes {@link #groupService}, {@link #departmentService},
     * {@link #courseService} properties using Spring
     *
     * @param groupService      {@link GroupService} bean
     * @param departmentService {@link DepartmentService} bean
     * @param courseService     {@link CourseService} bean
     */
    @Autowired
    public GroupController(GroupService groupService, DepartmentService departmentService, CourseService courseService) {
        this.groupService = groupService;
        this.departmentService = departmentService;
        this.courseService = courseService;
    }

    /**
     * Returns {@link AbstractService} with value {@link Group} for using inherited methods
     *
     * @return {@link AbstractService} with value {@link Group} for using inherited methods
     * @see AbstractController#getCrudService()
     */
    @Override
    protected AbstractService<Group> getCrudService() {
        return groupService;
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
     * <p>Mapping for GET request sends three models:
     * {@link GroupDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Department} objects in database with attribute name: {@link #DEPARTMENTS_ATTRIBUTE_NAME},
     * list of {@link Course} objects in database with attribute name: {@link #COURSES_ATTRIBUTE_NAME}
     *
     * @param model model to send to view
     * @param group new {@link Group} object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with empty fields
     */
    @Override
    @GetMapping("/new")
    public String newEntity(Model model, Group group) {

        GroupDTO groupDTO = new GroupDTO(group);
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, groupDTO);
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        model.addAttribute(COURSES_ATTRIBUTE_NAME, courseService.getAll());
        return VIEW_FOLDER_NAME + EDIT_VIEW;
    }

    /**
     * Returns {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields.
     *
     * <p>Mapping for GET request sends three models:
     * {@link GroupDTO} with attribute name: {@link #ENTITY_ATTRIBUTE_NAME},
     * list of {@link Department} objects in database with attribute name: {@link #DEPARTMENTS_ATTRIBUTE_NAME},
     * list of {@link Course} objects in database with attribute name: {@link #COURSES_ATTRIBUTE_NAME}
     *
     * @param model model to send to view
     * @param id    {@link #ID} of {@link Group}object
     * @return {@link #EDIT_VIEW} in folder: {@link #VIEW_FOLDER_NAME} with filled fields
     */
    @Override
    @GetMapping("/{id}/edit")
    protected String edit(Model model, @PathVariable(ID) long id) {

        GroupDTO groupDTO = new GroupDTO(groupService.getById(id));
        model.addAttribute(ENTITY_ATTRIBUTE_NAME, groupDTO);
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departmentService.getAll());
        model.addAttribute(COURSES_ATTRIBUTE_NAME, courseService.getAll());
        return this.getViewFolderName() + EDIT_VIEW;
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for POST request casts model to {@link GroupDTO}
     * object. After, retrieved {@link GroupDTO} object converts to {@link Group}
     * object and saves to database
     *
     * @param groupDTO retrieved from model {@link GroupDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see GroupDTO#getGroup(AbstractService, AbstractService)
     */
    @PostMapping
    protected String create(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) GroupDTO groupDTO) {

        Group group = groupDTO.getGroup(departmentService, courseService);
        groupService.create(group);
        return REDIRECT + getViewFolderName();
    }

    /**
     * Returns {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     *
     * <p>Mapping for PUT request casts model to {@link GroupDTO}
     * object. After, retrieved {@link GroupDTO} object converts to {@link Group}
     * object and updates in database
     *
     * @param groupDTO retrieved from model {@link GroupDTO} object
     * @return {@link #REDIRECT} to {@link #INDEX_VIEW} of folder with name: {@link #VIEW_FOLDER_NAME}
     * @see AbstractService#create(Object)
     * @see GroupDTO#getGroup(AbstractService, AbstractService)
     */
    @PutMapping("/{id}")
    protected String update(@ModelAttribute(ENTITY_ATTRIBUTE_NAME) GroupDTO groupDTO) {

        Group group = groupDTO.getGroup(departmentService, courseService);
        groupService.update(group);
        return REDIRECT + getViewFolderName();
    }
}
